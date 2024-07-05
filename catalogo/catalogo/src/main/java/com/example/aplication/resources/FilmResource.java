package com.example.aplication.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.FilmDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/peliculas/v1")
public class FilmResource {
	private FilmService srv;

	public FilmResource(FilmService srv) {
		this.srv = srv;
	}
	
	@GetMapping
	public List getAll(@RequestParam(required = false, defaultValue = "largo") String modo) {
		return srv.getAll();
	}
	
	@GetMapping(params = "page")
	public Page<FilmDTO> getAll(Pageable page) {
		return srv.getByProjection(page, FilmDTO.class);
	}
	
	@GetMapping(path = "/{id}")
	public FilmDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id}/actores")
	@Transactional
	public List<Actor> getActor(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getActors();
	}
	
	@GetMapping(path = "/{id}/categoria")
	@Transactional
	public List<Category> getCategoria(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getCategories();
	}
	
	@GetMapping(path = "/{id}/idioma")
	@Transactional
	public Language getLanguage(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getLanguage();
	}
	
	@GetMapping(path = "/{id}/vo")
	@Transactional
	public Language getLanguageVO(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getLanguageVO();
	}
		
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody FilmDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(FilmDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody FilmDTO item) throws NotFoundException, InvalidDataException, BadRequestException {
		if(id != item.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(FilmDTO.from(item));
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}