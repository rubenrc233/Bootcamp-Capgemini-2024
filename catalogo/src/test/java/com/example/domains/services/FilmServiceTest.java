package com.example.domains.services;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.example.domains.services.FilmServiceImpl;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceTest {

    private Film createValidFilm(int id) {
        Film film = new Film(id);
        film.setTitle("A valid title");
        film.setRentalRate(new BigDecimal("2.99"));
        film.setReplacementCost(new BigDecimal("19.99"));
        film.setLanguage(new Language(1, "English"));
        film.setActors(new ArrayList<Actor>());
        film.setCategories(new ArrayList<Category>());
        return film;
    }
    
    @Mock
    private FilmRepository dao;

    @InjectMocks
    private FilmServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Métodos de proyección")
    class MetodosDeProyeccion {

        @Test
        @DisplayName("Obtener por proyección")
        void testGetByProjection() {
            when(dao.findAllBy(any())).thenReturn(List.of());
            List<Object> result = service.getByProjection(Object.class);
            assertNotNull(result);
            verify(dao, times(1)).findAllBy(Object.class);
        }

        @Test
        @DisplayName("Obtener por proyección con orden")
        void testGetByProjectionWithSort() {
            when(dao.findAllBy(any(Sort.class), any())).thenReturn(List.of());
            Iterable<Object> result = service.getByProjection(Sort.by("name"), Object.class);
            assertNotNull(result);
            verify(dao, times(1)).findAllBy(any(Sort.class), any());
        }

        @Test
        @DisplayName("Obtener por proyección con paginación")
        void testGetByProjectionWithPageable() {
            Page<Object> page = mock(Page.class);
            when(dao.findAllBy(any(Pageable.class), any())).thenReturn(page);
            Page<Object> result = service.getByProjection(Pageable.unpaged(), Object.class);
            assertNotNull(result);
            verify(dao, times(1)).findAllBy(any(Pageable.class), any());
        }
    }

    @Nested
    @DisplayName("Métodos de obtención")
    class MetodosDeObtencion {

        @Test
        @DisplayName("Obtener todos con orden")
        void testGetAllWithSort() {
            when(dao.findAll(any(Sort.class))).thenReturn(List.of());
            Iterable<Film> result = service.getAll(Sort.by("title"));
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Sort.class));
        }

        @Test
        @DisplayName("Obtener todos con paginación")
        void testGetAllWithPageable() {
            Page<Film> page = mock(Page.class);
            when(dao.findAll(any(Pageable.class))).thenReturn(page);
            Page<Film> result = service.getAll(Pageable.unpaged());
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Pageable.class));
        }

        @Test
        @DisplayName("Obtener todos")
        void testGetAll() {
            when(dao.findAll()).thenReturn(List.of());
            List<Film> result = service.getAll();
            assertNotNull(result);
            verify(dao, times(1)).findAll();
        }

        @Test
        @DisplayName("Obtener uno por ID")
        void testGetOne() {
            Film film = new Film(1);
            when(dao.findById(anyInt())).thenReturn(Optional.of(film));
            Optional<Film> result = service.getOne(1);
            assertTrue(result.isPresent());
            assertEquals(film, result.get());
            verify(dao, times(1)).findById(1);
        }
    }

    @Nested
    @DisplayName("Métodos de modificación")
    class MetodosDeModificacion {

        @Test
        @DisplayName("Agregar film válido")
        void testAddValidFilm() throws DuplicateKeyException, InvalidDataException {
            Film film = createValidFilm(0);
            when(dao.save(any(Film.class))).thenReturn(film);
            Film result = service.add(film);
            assertNotNull(result);
            assertEquals(film, result);
            verify(dao, times(2)).save(film);
        }

        @Test
        @DisplayName("Agregar film nulo")
        void testAddNullFilm() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Agregar film inválido")
        void testAddInvalidFilm() {
            Film film = mock(Film.class);
            when(film.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(film));
            assertNotNull(exception);
            verify(film, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Agregar film duplicado")
        void testAddDuplicateFilm() {
            Film film = new Film(1);
            film.setTitle("A Valid Title");
            film.setRentalRate(BigDecimal.valueOf(2.99));
            film.setReplacementCost(BigDecimal.valueOf(19.99));
            film.setLanguage(new Language(1, "English"));
            when(dao.existsById(anyInt())).thenReturn(true);
            when(dao.save(any(Film.class))).thenReturn(film);
            when(dao.existsById(anyInt())).thenReturn(true);
            DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> service.add(film));
            assertEquals(film.getErrorsMessage(), exception.getMessage());
        }

        @Test
        @DisplayName("Modificar film válido")
        void testModifyValidFilm() throws NotFoundException, InvalidDataException {
            Film film = createValidFilm(1);
            when(dao.findById(anyInt())).thenReturn(Optional.of(film));
            when(dao.save(any(Film.class))).thenReturn(film);
            
            Film result = service.modify(film);
            
            assertNotNull(result);
            assertEquals(film, result);
            verify(dao, times(1)).save(any(Film.class));
        }

        @Test
        @DisplayName("Modificar film nulo")
        void testModifyNullFilm() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar film inválido")
        void testModifyInvalidFilm() {
            Film film = mock(Film.class);
            when(film.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(film));
            assertNotNull(exception);
            verify(film, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Modificar film no encontrado")
        void testModifyFilmNotFound() {
            Film film = new Film(0);
            film.setTitle("A Valid Title");
            film.setRentalRate(BigDecimal.valueOf(2.99));
            film.setReplacementCost(BigDecimal.valueOf(19.99));
            film.setLanguage(new Language(1, "English"));
            when(dao.existsById(anyInt())).thenReturn(true);
            when(dao.save(any(Film.class))).thenReturn(film);
            when(dao.findById(anyInt())).thenReturn(Optional.empty());
            NotFoundException exception = assertThrows(NotFoundException.class, () -> service.modify(film));
            assertEquals("Not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Métodos de eliminación")
    class MetodosDeEliminacion {

        @Test
        @DisplayName("Eliminar film válido")
        void testDeleteValidFilm() throws InvalidDataException {
            Film film = new Film(1);
            doNothing().when(dao).delete(any(Film.class));
            service.delete(film);
            verify(dao, times(1)).deleteById(1);
        }

        @Test
        @DisplayName("Eliminar film nulo")
        void testDeleteNullFilm() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.delete(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Eliminar film por ID")
        void testDeleteFilmById() {
            doNothing().when(dao).deleteById(anyInt());
            service.deleteById(1);
            verify(dao, times(1)).deleteById(1);
        }
    }
}
