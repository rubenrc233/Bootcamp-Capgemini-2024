package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/actores")
@Schema(description = "Recurso que gestiona las operaciones relacionadas con los actores")
public class ActorResource {
    private final ActorService srv;

    public ActorResource(ActorService srv) {
        this.srv = srv;
    }

    @SuppressWarnings("rawtypes")
    @GetMapping(path = "/v1")
    @Operation(summary = "Obtener todos los actores")
    public List getAll(
            @Parameter(description = "Modo de visualización, puede ser 'largo' o 'short'", example = "largo") @RequestParam(required = false, defaultValue = "largo") String modo) {
        if ("short".equals(modo))
            return srv.getByProjection(ActorShort.class);
        else
            return srv.getAll();
    }

    @GetMapping(path = "/v2")
    @Operation(summary = "Obtener todos los actores con ordenamiento")
    public List<ActorDTO> getAllv2(
            @Parameter(description = "Campo por el cual ordenar la lista", example = "nombre") @RequestParam(required = false) String sort) {
        if (sort != null)
            return (List<ActorDTO>) srv.getByProjection(Sort.by(sort), ActorDTO.class);
        return srv.getByProjection(ActorDTO.class);
    }

    @GetMapping(path = { "/v1", "/v2" }, params = "page")
    @Operation(summary = "Obtener todos los actores con paginación")
    public Page<ActorShort> getAll(
            @ParameterObject Pageable page) {
        return srv.getByProjection(page, ActorShort.class);
    }

    @GetMapping(path = { "/v1/{id}", "/v2/{id}" })
    @Operation(summary = "Obtener un actor por su ID")
    public ActorDTO getOne(
            @Parameter(description = "ID del actor a buscar", example = "1") @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return ActorDTO.from(item.get());
    }

    record Peli(
            @Schema(description = "ID de la película") int id,
            @Schema(description = "Título de la película") String titulo) {}

    @GetMapping(path = { "/v1/{id}/pelis", "/v2/{id}/pelis" })
    @Transactional
    @Operation(summary = "Obtener películas de un actor por su ID")
    public List<Peli> getPelis(
            @Parameter(description = "ID del actor", example = "1") @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return item.get().getFilmActors().stream()
                .map(o -> new Peli(o.getFilm().getFilmId(), o.getFilm().getTitle()))
                .toList();
    }

    @DeleteMapping(path = "/v1/{id}/jubilacion")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Jubilar a un actor por su ID")
    public void jubilar(
            @Parameter(description = "ID del actor", example = "1") @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        item.get().jubilate();
    }

    @PostMapping(path = { "/v1", "/v2" })
    @Operation(summary = "Crear un nuevo actor")
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos del actor a crear") @Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(ActorDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getActorId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = { "/v1/{id}", "/v2/{id}" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualizar un actor por su ID")
    public void update(
            @Parameter(description = "ID del actor a actualizar", example = "1") @PathVariable int id,
            @Parameter(description = "Datos del actor actualizados") @Valid @RequestBody ActorDTO item) throws NotFoundException, InvalidDataException, BadRequestException {
        if (id != item.getActorId())
            throw new BadRequestException("No coinciden los identificadores");
        srv.modify(ActorDTO.from(item));
    }

    @DeleteMapping(path = { "/v1/{id}", "/v2/{id}" })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un actor por su ID")
    public void delete(
            @Parameter(description = "ID del actor a eliminar", example = "1") @PathVariable int id) {
        srv.deleteById(id);
    }
}
