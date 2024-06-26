package com.example.domains.services;

import com.example.domains.contracts.repositories.CategoryRepository;
import com.example.domains.entities.Category;
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

class CategoryServiceTest {

    @Mock
    private CategoryRepository dao;

    @InjectMocks
    private CategoryServiceImpl service;

    private Category createValidCategory(int id) {
        Category category = new Category(id);
        category.setName("Valid Category");
        return category;
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
            when(dao.findAllBy(any())).thenReturn(List.of(new Category()));
            List<Object> result = service.getByProjection(Object.class);
            assertNotNull(result);
            verify(dao, times(1)).findAllBy(Object.class);
        }

        @Test
        @DisplayName("Obtener por proyección con orden")
        void testGetByProjectionWithSort() {
            when(dao.findAllBy(any(Sort.class), any())).thenReturn(List.of(new Category()));
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
            when(dao.findAll(any(Sort.class))).thenReturn(List.of(createValidCategory(1)));
            Iterable<Category> result = service.getAll(Sort.by("name"));
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Sort.class));
        }

        @Test
        @DisplayName("Obtener todos con paginación")
        void testGetAllWithPageable() {
            Page<Category> page = mock(Page.class);
            when(dao.findAll(any(Pageable.class))).thenReturn(page);
            Page<Category> result = service.getAll(Pageable.unpaged());
            assertNotNull(result);
            verify(dao, times(1)).findAll(any(Pageable.class));
        }

        @Test
        @DisplayName("Obtener todos")
        void testGetAll() {
            when(dao.findAll()).thenReturn(List.of(createValidCategory(1)));
            List<Category> result = service.getAll();
            assertNotNull(result);
            verify(dao, times(1)).findAll();
        }

        @Test
        @DisplayName("Obtener uno por ID")
        void testGetOne() {
            Category category = createValidCategory(1);
            when(dao.findById(anyInt())).thenReturn(Optional.of(category));
            Optional<Category> result = service.getOne(1);
            assertTrue(result.isPresent());
            assertEquals(category, result.get());
            verify(dao, times(1)).findById(1);
        }
    }

    @Nested
    @DisplayName("Métodos de modificación")
    class MetodosDeModificacion {

        @Test
        @DisplayName("Agregar categoría válida")
        void testAddValidCategory() throws DuplicateKeyException, InvalidDataException {
            Category category = createValidCategory(0);
            when(dao.save(any(Category.class))).thenReturn(category);
            Category result = service.add(category);
            assertNotNull(result);
            assertEquals(category, result);
            verify(dao, times(1)).save(any(Category.class));
        }

        @Test
        @DisplayName("Agregar categoría nula")
        void testAddNullCategory() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(null));
            assertEquals("Faltan datos", exception.getMessage());
        }

        @Test
        @DisplayName("Agregar categoría inválida")
        void testAddInvalidCategory() {
            Category category = mock(Category.class);
            when(category.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.add(category));
            assertNotNull(exception);
            verify(category, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Agregar categoría duplicada")
        void testAddDuplicateCategory() {
            Category category = createValidCategory(1);
            when(service.getOne(anyInt())).thenReturn(Optional.of(category));
            DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> service.add(category));
            assertEquals("Ya existe esa categoria", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar categoría válida")
        void testModifyValidCategory() throws NotFoundException, InvalidDataException {
            Category category = createValidCategory(1);
            when(dao.findById(anyInt())).thenReturn(Optional.of(category));
            when(dao.save(any(Category.class))).thenReturn(category);
            Category result = service.modify(category);
            assertNotNull(result);
            assertEquals(category, result);
            verify(dao, times(1)).save(any(Category.class));
        }

        @Test
        @DisplayName("Modificar categoría nula")
        void testModifyNullCategory() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(null));
            assertEquals("Faltan los datos", exception.getMessage());
        }

        @Test
        @DisplayName("Modificar categoría inválida")
        void testModifyInvalidCategory() {
            Category category = mock(Category.class);
            when(category.isInvalid()).thenReturn(true);
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.modify(category));
            assertNotNull(exception);
            verify(category, times(1)).isInvalid();
        }

        @Test
        @DisplayName("Modificar categoría no encontrada")
        void testModifyCategoryNotFound() {
            Category category = createValidCategory(1);
            when(dao.findById(anyInt())).thenReturn(Optional.empty());
            NotFoundException exception = assertThrows(NotFoundException.class, () -> service.modify(category));
            assertEquals("Not found", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Métodos de eliminación")
    class MetodosDeEliminacion {

        @Test
        @DisplayName("Eliminar categoría válida")
        void testDeleteValidCategory() throws InvalidDataException {
            Category category = createValidCategory(1);
            doNothing().when(dao).delete(any(Category.class));
            service.delete(category);
            verify(dao, times(1)).delete(any(Category.class));
        }

        @Test
        @DisplayName("Eliminar categoría nula")
        void testDeleteNullCategory() {
            InvalidDataException exception = assertThrows(InvalidDataException.class, () -> service.delete(null));
            assertEquals("No puede ser nulo", exception.getMessage());
        }

        @Test
        @DisplayName("Eliminar categoría por ID")
        void testDeleteCategoryById() {
            doNothing().when(dao).deleteById(anyInt());
            service.deleteById(1);
            verify(dao, times(1)).deleteById(1);
        }
    }
}
