package com.example.domains.services;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Language;
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

class LanguageServiceTest {

    @Mock
    private LanguageRepository dao;

    @InjectMocks
    private LanguageServiceImpl service;

    private Language createValidLanguage(int id) {
        Language language = new Language(id, "English");
        return language;
    }

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
            Iterable<Language> result = service.getAll(Sort.by("name"));
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Sort.class));
        }

        @Test
        @DisplayName("Obtener todos con paginación")
        void testGetAllWithPageable() {
            @SuppressWarnings("unchecked")
			Page<Language> page = mock(Page.class);
            when(dao.findAll(any(Pageable.class))).thenReturn(page);
            Page<Language> result = service.getAll(Pageable.unpaged());
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Pageable.class));
        }

        @Test
        @DisplayName("Obtener todos")
        void testGetAll() {
            when(dao.findAll()).thenReturn(List.of());
            List<Language> result = service.getAll();
            assertNotNull(result);
            verify(dao, times(1)).findAll();
        }

        @Test
        @DisplayName("Obtener uno por ID")
        void testGetOne() {
            Language language = createValidLanguage(1);
            when(dao.findById(anyInt())).thenReturn(Optional.of(language));
            Optional<Language> result = service.getOne(1);
            assertTrue(result.isPresent());
            assertEquals(language, result.get());
            verify(dao, times(1)).findById(1);
        }
    }

    @Nested
    @DisplayName("Métodos de modificación")
    class MetodosDeModificacion {

        @Test
        @DisplayName("Agregar idioma válido")
        void testAddValidLanguage() throws DuplicateKeyException, InvalidDataException {
            Language language = createValidLanguage(0);
            when(dao.save(any(Language.class))).thenReturn(language);
            
            Language result = service.add(language);

            assertNotNull(result);
            assertEquals(language, result);
            verify(dao, times(1)).save(any(Language.class));
        }

        @Test
        @DisplayName("Agregar idioma nulo")
        void testAddNullLanguage() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Agregar idioma inválido")
        void testAddInvalidLanguage() {
            Language language = mock(Language.class);
            when(language.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(language));
            assertNotNull(exception);
            verify(language, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Agregar idioma duplicado")
        void testAddDuplicateLanguage() {
            Language language = createValidLanguage(1);
            when(dao.existsById(anyInt())).thenReturn(true);
            DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> service.add(language));
            assertEquals("Ya existe", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar idioma válido")
        void testModifyValidLanguage() throws NotFoundException, InvalidDataException {
            Language language = createValidLanguage(1);
            when(dao.existsById(anyInt())).thenReturn(true);
            when(dao.save(any(Language.class))).thenReturn(language);
            
            Language result = service.modify(language);
            
            assertNotNull(result);
            assertEquals(language, result);
            verify(dao, times(1)).save(any(Language.class));
        }

        @Test
        @DisplayName("Modificar idioma nulo")
        void testModifyNullLanguage() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar idioma inválido")
        void testModifyInvalidLanguage() {
            Language language = mock(Language.class);
            when(language.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(language));
            assertNotNull(exception);
            verify(language, times(1)).isInvalid();
        }
    }

    @Nested
    @DisplayName("Métodos de eliminación")
    class MetodosDeEliminacion {

        @Test
        @DisplayName("Eliminar idioma válido")
        void testDeleteValidLanguage() throws InvalidDataException {
            Language language = createValidLanguage(1);
            doNothing().when(dao).delete(any(Language.class));
            service.delete(language);
            verify(dao, times(1)).delete(language);
        }

        @Test
        @DisplayName("Eliminar idioma nulo")
        void testDeleteNullLanguage() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.delete(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Eliminar idioma por ID")
        void testDeleteLanguageById() {
            doNothing().when(dao).deleteById(anyInt());
            service.deleteById(1);
            verify(dao, times(1)).deleteById(1);
        }
    }
}
