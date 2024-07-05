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

import com.example.aplication.resources.ActorResource.Peli;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.models.LanguageDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/language/v1")
public class LanguageResource {
	private LanguageService srv;

	public LanguageResource(LanguageService srv) {
		this.srv = srv;
	}
	
	@GetMapping
	public List getAll(@RequestParam(required = false, defaultValue = "largo") String modo) {
		if("short".equals(modo))
			return srv.getByProjection(LanguageDTO.class);
		else
			return srv.getAll(); // srv.getByProjection(ActorDTO.class);
	}
	
	@GetMapping(params = "page")
	public Page<LanguageDTO> getAll(Pageable page) {
		return srv.getByProjection(page, LanguageDTO.class);
	}
	
	@GetMapping(path = "/{id}")
	public LanguageDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return LanguageDTO.from(item.get());
	}
	
record Peli(int id, String titulo) {}
	
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<Peli> getPelis(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilms().stream()
				.map(o -> new Peli(o.getFilmId(), o.getTitle()))
				.toList();
	}
	
	@GetMapping(path = "/{id}/pelisVO")
	@Transactional
	public List<Peli> getPelisVO(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmsVO().stream()
				.map(o -> new Peli(o.getFilmId(), o.getTitle()))
				.toList();
	}
		
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody LanguageDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(LanguageDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody LanguageDTO item) throws NotFoundException, InvalidDataException, BadRequestException {
		if(id != item.getLanguageId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(LanguageDTO.from(item));
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}