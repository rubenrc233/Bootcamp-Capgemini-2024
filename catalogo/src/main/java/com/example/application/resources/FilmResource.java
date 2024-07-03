package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.FilmDTO;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping(path = "/peliculas/v1")
public class FilmResource {
	@Autowired
	private FilmService srv;

	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAll(Pageable pageable,@RequestParam(defaultValue = "short") String mode) {
		return srv.getByProjection(pageable, FilmShortDTO.class);
	}
	

	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam(defaultValue = "short") String mode) {
		return srv.getByProjection(FilmShortDTO.class);
	}

	@GetMapping(path = "/{id}")
	public FilmDTO getOne(@PathVariable int id)
			throws Exception {
		Optional<Film> film = srv.getOne(id);
		if (film.isEmpty())
			throw new NotFoundException();
		return FilmDTO.from(film.get());
	}

	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(@PathVariable int id)throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getActors().stream().map(item -> ActorDTO.from(item)).toList();
	}

	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(@PathVariable int id) throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getCategories();
	}

	@GetMapping(path = "/clasificaciones")
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> create(@RequestBody FilmDTO item) throws Exception {
		Film newItem = srv.add(FilmDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newItem.getFilmId())
				.toUri();
		return ResponseEntity.created(location).build();
	}


	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public FilmDTO edit(@PathVariable int id,@Valid @RequestBody FilmDTO item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los identificadores");
		return FilmDTO.from(srv.modify(FilmDTO.from(item)));
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@RequestParam(value = "Identificador de la pelicula", required = true) @PathVariable int id)
			throws Exception {
		srv.deleteById(id);
	}
	
	record Search(
			String title, 
			Integer minlength, 
			Integer maxlength, 
			String rating,
			String mode
			) {}

	@Operation(summary = "Consulta filtrada de peliculas")
	@GetMapping("/filtro")
	public List<?> search(@ParameterObject @Valid Search filter) throws BadRequestException {
		if(filter.minlength != null && filter.maxlength != null && filter.minlength > filter.maxlength)
				throw new BadRequestException("la duración máxima debe ser superior a la mínima");
		Specification<Film> spec = null;
		if(filter.title != null && !"".equals(filter.title)) {
			Specification<Film> cond = (root, query, builder) -> builder.like(root.get("title"), "%" + filter.title.toUpperCase() + "%");
			spec = spec == null ? cond : spec.and(cond);
		}
		if(filter.rating != null && !"".equals(filter.rating)) {
			if(!List.of(Rating.VALUES).contains(filter.rating))
				throw new BadRequestException("rating desconocido");
			Specification<Film> cond = (root, query, builder) -> builder.equal(root.get("rating"), Rating.getEnum(filter.rating));
			spec = spec == null ? cond : spec.and(cond);
		}
		if(filter.minlength != null) {
			Specification<Film> cond = (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("length"), filter.minlength);
			spec = spec == null ? cond : spec.and(cond);
		}
		if(filter.maxlength != null) {
			Specification<Film> cond = (root, query, builder) -> builder.lessThanOrEqualTo(root.get("length"), filter.maxlength);
			spec = spec == null ? cond : spec.and(cond);
		}
		if(spec == null)
			throw new BadRequestException("Faltan los parametros de filtrado");
		var query = srv.getAll(spec).stream();
		if("short".equals(filter.mode))
			return query.map(e -> FilmShortDTO.from(e)).toList();
		else {
			return query.map(e -> FilmDTO.from(e)).toList();
		}
	}

}