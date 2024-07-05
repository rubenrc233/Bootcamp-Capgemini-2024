package com.example.domains.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.entities.Film;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

class FilmServiceImplTest {
	
	FilmServiceImpl srv;
	FilmRepository dao;
	@BeforeEach
	void preparation() {
		dao = mock(FilmRepository.class);
		srv = new FilmServiceImpl(dao);
	}

	@Nested
	@DisplayName("Pruebas de agregar")
	class Add{
		@Nested
		class OK{
			@Test
			@DisplayName("Agregar correcta")
			void test1(){
				var test = mock(Film.class);
				when(test.isValid()).thenReturn(true);
				try {
					srv.add(test);
				} catch (DuplicateKeyException e) {
					e.printStackTrace();
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				verify(dao).save(test);
			}

		}
		@Nested
		class KO{
			@Test
			@DisplayName("Datos incorrectos, no es valido")
			void test1(){
				var test = mock(Film.class);
				when(test.isInvalid()).thenReturn(true);
				assertThrows(InvalidDataException.class, () -> srv.add(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, es null")
			void test2() {
				Film test = null;
				assertThrows(InvalidDataException.class, () -> srv.add(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, ya esta añadido")
			void test3() {
				var test = mock(Film.class);
				when(test.isInvalid()).thenReturn(false);
				when(dao.existsById(anyInt())).thenReturn(true);
				assertThrows(DuplicateKeyException.class, () -> srv.add(test));
			}
			
		}
	}
	
	
	@Nested
	@DisplayName("Pruebas de modificar")
	class Modify{
		@Nested
		class OK{
			@Test
			@DisplayName("Datos correcta")
			void test1(){
				var test = mock(Film.class);
				when(test.isValid()).thenReturn(true);
				when(test.getFilmId()).thenReturn(1);
				when(dao.existsById(anyInt())).thenReturn(true);
					try {
						srv.modify(test);
					} catch (NotFoundException e) {
						e.printStackTrace();
					} catch (InvalidDataException e) {
						e.printStackTrace();
					}
				verify(dao).save(test);
			}

		}
		@Nested
		class KO{
			@Test
			@DisplayName("Datos incorrectos,no es valido")
			void test1(){
				var test = mock(Film.class);
				when(test.isInvalid()).thenReturn(true);
				assertThrows(InvalidDataException.class, () -> srv.modify(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, es null")
			void test2() {
				Film test = null;
				assertThrows(InvalidDataException.class, () -> srv.modify(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos,no esta añadido id distito de 0")
			void test3() {
				var test = mock(Film.class);
				when(test.isInvalid()).thenReturn(false);
				when(test.getFilmId()).thenReturn(1);
				when(dao.existsById(anyInt())).thenReturn(false);
				assertThrows(NotFoundException.class, () -> srv.modify(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, no esta añadido id 0")
			void test4() {
				var test = mock(Film.class);
				when(test.isInvalid()).thenReturn(false);
				when(test.getFilmId()).thenReturn(0);
				assertThrows(NotFoundException.class, () -> srv.modify(test));
			}
			
		}
	}
	
	
	@Nested
	@DisplayName("Pruebas de elimirar")
	class Delete{
		@Nested
		class OK{
			@Test
			@DisplayName("Eliminar correcto")
			void test1(){
				var test = new Film(1);
					try {
						srv.delete(test);
					} catch (InvalidDataException e) {
						e.printStackTrace();
					}
				verify(dao).delete(test);
			}

		}
		@Nested
		class KO{
			@Test
			@DisplayName("Datos incorrectos, es null")
			void test2() {
				Film test = null;
				assertThrows(InvalidDataException.class, () -> srv.delete(test));
			}
			
		}
	}
	
	 @Nested
	    @DisplayName("Pruebas de obtener uno")
	    class GetOne {
	        @Test
	        @DisplayName("Obtener uno correcto")
	        void test1() {
	            var expected = Optional.of(new Film(1));
	            when(dao.findById(1)).thenReturn(expected);
	            var result = srv.getOne(1);
	            assertEquals(expected, result);
	        }

	        @Test
	        @DisplayName("Obtener uno incorrecto")
	        void test2() {
	            when(dao.findById(1)).thenReturn(Optional.empty());
	            var result = srv.getOne(1);
	            assertEquals(Optional.empty(), result);
	        }
	    }

	    @Nested
	    @DisplayName("Pruebas de obtener todos")
	    class GetAll {
	        @Test
	        @DisplayName("Obtener todos correcto")
	        void test1() {
	            var expected = List.of(new Film(1), new Film(2));
	            when(dao.findAll()).thenReturn(expected);
	            var result = srv.getAll();
	            assertEquals(expected, result);
	        }

	        @Test
	        @DisplayName("Obtener todos con Sort correcto")
	        void test2() {
	            var expected = List.of(new Film(1), new Film(2));
	            var sort = Sort.by("title");
	            when(dao.findAll(sort)).thenReturn(expected);
	            var result = srv.getAll(sort);
	            assertEquals(expected, result);
	        }

	        @Test
	        @DisplayName("Obtener todos con Pageable correcto")
	        void test3() {
	            Page<Film> expected = mock(Page.class);
	            var pageable = mock(Pageable.class);
	            when(dao.findAll(pageable)).thenReturn(expected);
	            var result = srv.getAll(pageable);
	            assertEquals(expected, result);
	        }
	    }

	    @Nested
	    @DisplayName("Pruebas de obtener por proyección")
	    class GetByProjection {
	        @Test
	        @DisplayName("Obtener por proyección correcto")
	        void test1() {
	            var expected = List.of(mock(Object.class));
	            when(dao.findAllBy(Object.class)).thenReturn(expected);
	            var result = srv.getByProjection(Object.class);
	            assertEquals(expected, result);
	        }

	        @Test
	        @DisplayName("Obtener por proyección con Sort correcto")
	        void test2() {
	            var expected = List.of(mock(Object.class));
	            var sort = Sort.by("title");
	            when(dao.findAllBy(sort, Object.class)).thenReturn(expected);
	            var result = srv.getByProjection(sort, Object.class);
	            assertEquals(expected, result);
	        }

	        @Test
	        @DisplayName("Obtener por proyección con Pageable correcto")
	        void test3() {
	            Page<Object> expected = mock(Page.class);
	            var pageable = mock(Pageable.class);
	            when(dao.findAllBy(pageable, Object.class)).thenReturn(expected);
	            var result = srv.getByProjection(pageable, Object.class);
	            assertEquals(expected, result);
	        }
	    }

	    @Nested
	    @DisplayName("Pruebas de eliminar por id")
	    class DeleteById {
	        @Test
	        @DisplayName("Eliminar por id correcto")
	        void test1() {
	            srv.deleteById(1);
	            verify(dao).deleteById(1);
	        }
	    }


}
;