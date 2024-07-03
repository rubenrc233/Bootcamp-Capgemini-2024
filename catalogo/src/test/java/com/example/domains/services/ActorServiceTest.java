package com.example.domains.services;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorServiceTest {

    @Mock
    private ActorRepository dao;

    @InjectMocks
    private ActorServiceImpl service;

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
            @SuppressWarnings("unchecked")
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
            Iterable<Actor> result = service.getAll(Sort.by("name"));
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Sort.class));
        }

        @Test
        @DisplayName("Obtener todos con paginación")
        void testGetAllWithPageable() {
            @SuppressWarnings("unchecked")
			Page<Actor> page = mock(Page.class);
            when(dao.findAll(any(Pageable.class))).thenReturn(page);
            Page<Actor> result = service.getAll(Pageable.unpaged());
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Pageable.class));
        }

        @Test
        @DisplayName("Obtener todos")
        void testGetAll() {
            when(dao.findAll()).thenReturn(List.of());
            List<Actor> result = service.getAll();
            assertNotNull(result);
            verify(dao, times(1)).findAll();
        }

        @Test
        @DisplayName("Obtener uno por ID")
        void testGetOne() {
            Actor actor = new Actor(1, "John", "Doe");
            when(dao.findById(anyInt())).thenReturn(Optional.of(actor));
            Optional<Actor> result = service.getOne(1);
            assertTrue(result.isPresent());
            assertEquals(actor, result.get());
            verify(dao, times(1)).findById(1);
        }
    }

    @Nested
    @DisplayName("Métodos de modificación")
    class MetodosDeModificacion {

        @Test
        @DisplayName("Agregar actor válido")
        void testAddValidActor() throws DuplicateKeyException, InvalidDataException {
            Actor actor = new Actor(0, "John", "Doe");
            when(dao.save(any(Actor.class))).thenReturn(actor);
            Actor result = service.add(actor);
            assertNotNull(result);
            assertEquals(actor, result);
            verify(dao, times(1)).save(actor);
        }

        @Test
        @DisplayName("Agregar actor nulo")
        void testAddNullActor() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Agregar actor inválido")
        void testAddInvalidActor() {
            Actor actor = mock(Actor.class);
            when(actor.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(actor));
            assertNotNull(exception);
            verify(actor, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Agregar actor duplicado")
        void testAddDuplicateActor() {
            Actor actor = new Actor(1, "John", "Doe");
            when(dao.existsById(anyInt())).thenReturn(true);
            DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> service.add(actor));
            assertEquals("Ya existe", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar actor válido")
        void testModifyValidActor() throws NotFoundException, InvalidDataException {
            Actor actor = new Actor(1, "John", "Doe");
            when(dao.existsById(anyInt())).thenReturn(true);
            when(dao.save(any(Actor.class))).thenReturn(actor);
            Actor result = service.modify(actor);
            assertNotNull(result);
            assertEquals(actor, result);
            verify(dao, times(1)).save(actor);
        }

        @Test
        @DisplayName("Modificar actor nulo")
        void testModifyNullActor() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar actor inválido")
        void testModifyInvalidActor() {
            Actor actor = mock(Actor.class);
            when(actor.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(actor));
            assertNotNull(exception);
            verify(actor, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Modificar actor no encontrado")
        void testModifyActorNotFound() {
            Actor actor = new Actor(0, "John", "Doe");
            when(dao.existsById(anyInt())).thenReturn(false);
            NotFoundException exception = assertThrows(NotFoundException.class, () -> service.modify(actor));
            assertEquals("No existe", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Métodos de eliminación")
    class MetodosDeEliminacion {

        @Test
        @DisplayName("Eliminar actor válido")
        void testDeleteValidActor() throws InvalidDataException {
            Actor actor = new Actor(1, "John", "Doe");
            doNothing().when(dao).delete(any(Actor.class));
            service.delete(actor);
            verify(dao, times(1)).delete(actor);
        }

        @Test
        @DisplayName("Eliminar actor nulo")
        void testDeleteNullActor() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.delete(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Eliminar actor por ID")
        void testDeleteActorById() {
            doNothing().when(dao).deleteById(anyInt());
            service.deleteById(1);
            verify(dao, times(1)).deleteById(1);
        }
    }
}
