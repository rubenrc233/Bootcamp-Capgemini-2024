package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Servicio que maneja las operaciones relacionadas con los actores")
public class ActorServiceImpl implements ActorService {

    private final ActorRepository dao;

    public ActorServiceImpl(ActorRepository dao) {
        this.dao = dao;
    }

    @Override
    @Operation(summary = "Obtener todos los actores por proyección")
    public <T> List<T> getByProjection(
            @Parameter(description = "Tipo de proyección a utilizar", example = "ActorDTO") Class<T> type) {
        return dao.findAllBy(type);
    }

    @Override
    @Operation(summary = "Obtener todos los actores por proyección y orden")
    public <T> Iterable<T> getByProjection(
            @Parameter(description = "Orden a aplicar en la consulta", example = "Sort.by(\"nombre\")") Sort sort,
            @Parameter(description = "Tipo de proyección a utilizar", example = "ActorDTO") Class<T> type) {
        return dao.findAllBy(sort, type);
    }

    @Override
    @Operation(summary = "Obtener todos los actores por proyección con paginación")
    public <T> Page<T> getByProjection(
            @Parameter(description = "Configuración de paginación a aplicar en la consulta", example = "PageRequest.of(0, 10)") Pageable pageable,
            @Parameter(description = "Tipo de proyección a utilizar", example = "ActorDTO") Class<T> type) {
        return dao.findAllBy(pageable, type);
    }

    @Override
    @Operation(summary = "Obtener todos los actores con orden")
    public Iterable<Actor> getAll(
            @Parameter(description = "Orden a aplicar en la consulta", example = "Sort.by(\"nombre\")") Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    @Operation(summary = "Obtener todos los actores con paginación")
    public Page<Actor> getAll(
            @Parameter(description = "Configuración de paginación a aplicar en la consulta", example = "PageRequest.of(0, 10)") Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    @Operation(summary = "Obtener todos los actores")
    public List<Actor> getAll() {
        return dao.findAll();
    }

    @Override
    @Operation(summary = "Obtener un actor por su ID")
    public Optional<Actor> getOne(
            @Parameter(description = "ID del actor a buscar", example = "1") Integer id) {
        return dao.findById(id);
    }

    @Override
    @Operation(summary = "Añadir un nuevo actor")
    public Actor add(
            @Parameter(description = "Actor a añadir") Actor item) throws DuplicateKeyException, InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("No puede ser nulo");
        }
        if (item.isInvalid()) {
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        }
        if (item.getActorId() != 0 && dao.existsById(item.getActorId())) {
            throw new DuplicateKeyException("Ya existe");
        }
        return dao.save(item);
    }

    @Override
    @Operation(summary = "Modificar un actor existente")
    public Actor modify(
            @Parameter(description = "Actor a modificar") Actor item) throws NotFoundException, InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("No puede ser nulo");
        }
        if (item.isInvalid()) {
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        }
        if (item.getActorId() == 0 && !dao.existsById(item.getActorId())) {
            throw new NotFoundException("No existe");
        }
        return dao.save(item);
    }

    @Override
    @Operation(summary = "Eliminar un actor")
    public void delete(
            @Parameter(description = "Actor a eliminar") Actor item) throws InvalidDataException {
        if (item == null) {
            throw new InvalidDataException("No puede ser nulo");
        }
        dao.delete(item);
    }

    @Override
    @Operation(summary = "Eliminar un actor por su ID")
    public void deleteById(
            @Parameter(description = "ID del actor a eliminar", example = "1") Integer id) {
        dao.deleteById(id);
    }

    @Override
    @Operation(summary = "Obtener actores actualizados después de una fecha")
    public List<Actor> novedades(
            @Parameter(description = "Fecha desde la cual buscar actualizaciones", example = "2023-01-01T00:00:00Z") Timestamp fecha) {
        return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
    }
}
