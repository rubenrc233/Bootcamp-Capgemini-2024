package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PostMapping;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping(path = "/peliculas/v1")
@Schema(description = "Recurso que gestiona las operaciones relacionadas con las películas")
public class FilmResource {
    @Autowired
    private FilmService srv;

    @GetMapping(params = "page")
    @Operation(summary = "Obtener todas las películas en formato corto con paginación")
    public Page<FilmShortDTO> getAll(
            @Parameter(description = "Configuración de paginación a aplicar en la consulta", example = "PageRequest.of(0, 10)") Pageable pageable,
            @Parameter(description = "Modo de visualización, por defecto es 'short'", example = "short") @RequestParam(defaultValue = "short") String mode) {
        return srv.getByProjection(pageable, FilmShortDTO.class);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las películas en formato corto")
    public List<FilmShortDTO> getAll(
            @Parameter(description = "Modo de visualización, por defecto es 'short'", example = "short") @RequestParam(defaultValue = "short") String mode) {
        return srv.getByProjection(FilmShortDTO.class);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Obtener una película por su ID")
    public FilmDTO getOne(
            @Parameter(description = "ID de la película a buscar", example = "1") @PathVariable int id) throws Exception {
        Optional<Film> film = srv.getOne(id);
        if (film.isEmpty())
            throw new NotFoundException();
        return FilmDTO.from(film.get());
    }

    @GetMapping(path = "/{id}/reparto")
    @Transactional
    @Operation(summary = "Obtener el reparto de una película por su ID")
    public List<ActorDTO> getFilms(
            @Parameter(description = "ID de la película", example = "1") @PathVariable int id) throws Exception {
        Optional<Film> rslt = srv.getOne(id);
        if (rslt.isEmpty())
            throw new NotFoundException();
        return rslt.get().getActors().stream().map(item -> ActorDTO.from(item)).toList();
    }

    @GetMapping(path = "/{id}/categorias")
    @Transactional
    @Operation(summary = "Obtener las categorías de una película por su ID")
    public List<Category> getCategories(
            @Parameter(description = "ID de la película", example = "1") @PathVariable int id) throws Exception {
        Optional<Film> rslt = srv.getOne(id);
        if (rslt.isEmpty())
            throw new NotFoundException();
        return rslt.get().getCategories();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    @Operation(summary = "Crear una nueva película")
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos de la película a crear") @RequestBody FilmDTO item) throws Exception {
        Film newItem = srv.add(FilmDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newItem.getFilmId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Actualizar una película por su ID")
    public FilmDTO edit(
            @Parameter(description = "ID de la película a actualizar", example = "1") @PathVariable int id,
            @Parameter(description = "Datos de la película actualizados") @Valid @RequestBody FilmDTO item) throws Exception {
        if (item.getFilmId() != id)
            throw new BadRequestException("No coinciden los identificadores");
        return FilmDTO.from(srv.modify(FilmDTO.from(item)));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una película por su ID")
    public void delete(
            @Parameter(description = "ID de la película a eliminar", example = "1") @PathVariable int id)
            throws Exception {
        srv.deleteById(id);
    }

    record Search(
            @Schema(description = "Título de la película") String title, 
            @Schema(description = "Duración mínima de la película") Integer minlength, 
            @Schema(description = "Duración máxima de la película") Integer maxlength, 
            @Schema(description = "Clasificación de la película") String rating,
            @Schema(description = "Modo de visualización") String mode
    ) {}

    @Operation(summary = "Consulta filtrada de películas")
    @GetMapping("/filtro")
    public List<?> search(
            @ParameterObject @Valid Search filter) throws BadRequestException {
        if (filter.minlength != null && filter.maxlength != null && filter.minlength > filter.maxlength)
            throw new BadRequestException("la duración máxima debe ser superior a la mínima");
        Specification<Film> spec = null;
        if (filter.title != null && !"".equals(filter.title)) {
            Specification<Film> cond = (root, query, builder) -> builder.like(root.get("title"), "%" + filter.title.toUpperCase() + "%");
            spec = spec == null ? cond : spec.and(cond);
        }
        if (filter.rating != null && !"".equals(filter.rating)) {
            if (!List.of(Rating.VALUES).contains(filter.rating))
                throw new BadRequestException("rating desconocido");
            Specification<Film> cond = (root, query, builder) -> builder.equal(root.get("rating"), Rating.getEnum(filter.rating));
            spec = spec == null ? cond : spec.and(cond);
        }
        if (filter.minlength != null) {
            Specification<Film> cond = (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("length"), filter.minlength);
            spec = spec == null ? cond : spec.and(cond);
        }
        if (filter.maxlength != null) {
            Specification<Film> cond = (root, query, builder) -> builder.lessThanOrEqualTo(root.get("length"), filter.maxlength);
            spec = spec == null ? cond : spec.and(cond);
        }
        if (spec == null)
            throw new BadRequestException("Faltan los parametros de filtrado");
        var query = srv.getAll(spec).stream();
        if ("short".equals(filter.mode))
            return query.map(e -> FilmShortDTO.from(e)).toList();
        else {
            return query.map(e -> FilmDTO.from(e)).toList();
        }
    }

    @GetMapping(path = "/clasificaciones")
    @Operation(summary = "Listado de las clasificaciones por edades")
    public List<Map<String, String>> getClasificaciones() {
        return List.of(Map.of("key", "G", "value", "Todos los públicos"),
                Map.of("key", "PG", "value", "Guía paternal sugerida"),
                Map.of("key", "PG-13", "value", "Guía paternal estricta"), Map.of("key", "R", "value", "Restringido"),
                Map.of("key", "NC-17", "value", "Prohibido para audiencia de 17 años y menos"));
    }
}
