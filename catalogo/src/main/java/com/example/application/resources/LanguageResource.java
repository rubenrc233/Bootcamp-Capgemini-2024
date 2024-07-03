package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.domains.entities.dtos.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/idiomas/v1")
public class LanguageResource {
    @Autowired
    private LanguageService dao;

    @GetMapping
    @JsonView(Language.class)
    public List<Language> getAll() {
        return dao.getAll();
    }

    @GetMapping(path = "/{id}")
    @JsonView(Language.class)
    public Language getOne(@PathVariable int id) throws NotFoundException {
        return dao.getOne(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(path = "/{id}/peliculas")
    @Transactional
    public List<FilmShortDTO> getFilms(@PathVariable int id) throws NotFoundException {
        Language language = dao.getOne(id).orElseThrow(NotFoundException::new);
        return language.getFilms().stream()
                .map(FilmShortDTO::from)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}/vo")
    @Transactional
    public List<FilmShortDTO> getFilmsVO(@PathVariable int id) throws NotFoundException {
        Language language = dao.getOne(id).orElseThrow(NotFoundException::new);
        return language.getFilmsVO().stream()
                .map(FilmShortDTO::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> add(@Valid @RequestBody Language item) throws InvalidDataException, DuplicateKeyException {
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        if (dao.getOne(item.getLanguageId()).isPresent())
            throw new InvalidDataException("Duplicate key");
        dao.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getLanguageId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    public Language modify(@PathVariable int id, @Valid @RequestBody Language item) throws BadRequestException, InvalidDataException, NotFoundException {
        if (item.getLanguageId() != id)
            throw new BadRequestException("No coinciden los ID");
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        if (!dao.getOne(item.getLanguageId()).isPresent())
            throw new NotFoundException();
        dao.modify(item);
        return item;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws NotFoundException {
        if (dao.getOne(id).isEmpty()) {
            throw new NotFoundException("Missing item");
        }
        dao.deleteById(id);
    }

}
