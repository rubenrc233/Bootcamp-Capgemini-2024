package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Servicio que maneja las operaciones relacionadas con las películas")
public class FilmServiceImpl implements FilmService {
    private final FilmRepository dao;

    public FilmServiceImpl(FilmRepository dao) {
        this.dao = dao;
    }

    @Override
    @Operation(summary = "Obtener todas las películas por proyección")
    public <T> List<T> getByProjection(
            @Parameter(description = "Tipo de proyección a utilizar", example = "FilmDTO") @NonNull Class<T> type) {
        return dao.findAllBy(type);
    }

    @Override
    @Operation(summary = "Obtener todas las películas por proyección y orden")
    public <T> List<T> getByProjection(
            @Parameter(description = "Orden a aplicar en la consulta", example = "Sort.by(\"title\")") @NonNull Sort sort,
            @Parameter(description = "Tipo de proyección a utilizar", example = "FilmDTO") @NonNull Class<T> type) {
        return dao.findAllBy(sort, type);
    }

    @Override
    @Operation(summary = "Obtener todas las películas por proyección con paginación")
    public <T> Page<T> getByProjection(
            @Parameter(description = "Configuración de paginación a aplicar en la consulta", example = "PageRequest.of(0, 10)") @NonNull Pageable pageable,
            @Parameter(description = "Tipo de proyección a utilizar", example = "FilmDTO") @NonNull Class<T> type) {
        return dao.findAllBy(pageable, type);
    }

    @Override
    @Operation(summary = "Obtener todas las películas con orden")
    public List<Film> getAll(
            @Parameter(description = "Orden a aplicar en la consulta", example = "Sort.by(\"title\")") @NonNull Sort sort) {
        return dao.findAll(sort);
    }

    @Override
    @Operation(summary = "Obtener todas las películas con paginación")
    public Page<Film> getAll(
            @Parameter(description = "Configuración de paginación a aplicar en la consulta", example = "PageRequest.of(0, 10)") @NonNull Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    @Operation(summary = "Obtener todas las películas")
    public List<Film> getAll() {
        return dao.findAll();
    }

    @Override
    @Operation(summary = "Obtener una película por su ID")
    public Optional<Film> getOne(
            @Parameter(description = "ID de la película a buscar", example = "1") Integer id) {
        return dao.findById(id);
    }

    @Override
    @Operation(summary = "Obtener una película por especificación")
    public Optional<Film> getOne(
            @Parameter(description = "Especificación a aplicar en la consulta") @NonNull Specification<Film> spec) {
        return dao.findOne(spec);
    }

    @Override
    @Operation(summary = "Obtener todas las películas por especificación")
    public List<Film> getAll(
            @Parameter(description = "Especificación a aplicar en la consulta") @NonNull Specification<Film> spec) {
        return dao.findAll(spec);
    }

    @Override
    @Operation(summary = "Obtener todas las películas por especificación con paginación")
    public Page<Film> getAll(
            @Parameter(description = "Especificación a aplicar en la consulta") @NonNull Specification<Film> spec,
            @Parameter(description = "Configuración de paginación a aplicar en la consulta", example = "PageRequest.of(0, 10)") @NonNull Pageable pageable) {
        return dao.findAll(spec, pageable);
    }

    @Override
    @Operation(summary = "Obtener todas las películas por especificación y orden")
    public List<Film> getAll(
            @Parameter(description = "Especificación a aplicar en la consulta") @NonNull Specification<Film> spec,
            @Parameter(description = "Orden a aplicar en la consulta", example = "Sort.by(\"title\")") @NonNull Sort sort) {
        return dao.findAll(spec, sort);
    }

    @Override
    @Transactional
    @Operation(summary = "Añadir una nueva película")
    public Film add(
            @Parameter(description = "Película a añadir") Film item) throws DuplicateKeyException, InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        if (dao.existsById(item.getFilmId()))
            throw new DuplicateKeyException(item.getErrorsMessage());
        return dao.save(item);
    }

    @Override
    @Transactional
    @Operation(summary = "Modificar una película existente")
    public Film modify(
            @Parameter(description = "Película a modificar") Film item) throws NotFoundException, InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        if (item.isInvalid())
            throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
        var leido = dao.findById(item.getFilmId()).orElseThrow(() -> new NotFoundException());
        return dao.save(item.combine(leido));
    }

    @Override
    @Operation(summary = "Eliminar una película")
    public void delete(
            @Parameter(description = "Película a eliminar") Film item) throws InvalidDataException {
        if (item == null)
            throw new InvalidDataException("No puede ser nulo");
        deleteById(item.getFilmId());
    }

    @Override
    @Operation(summary = "Eliminar una película por su ID")
    public void deleteById(
            @Parameter(description = "ID de la película a eliminar", example = "1") Integer id) {
        dao.deleteById(id);
    }

    @Override
    @Operation(summary = "Obtener las películas actualizadas después de una fecha")
    public List<Film> novedades(
            @Parameter(description = "Fecha desde la cual buscar actualizaciones", example = "2023-01-01T00:00:00Z") @NonNull Timestamp fecha) {
        return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
    }
}
