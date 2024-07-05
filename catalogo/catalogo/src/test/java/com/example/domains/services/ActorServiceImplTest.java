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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

class ActorServiceImplTest {
	
	ActorServiceImpl srv;
	ActorRepository dao;
	@BeforeEach
	void preparation() {
		dao = mock(ActorRepository.class);
		srv = new ActorServiceImpl(dao);
	}

	@Nested
	@DisplayName("Pruebas de agregar actores")
	class Add{
		@Nested
		class OK{
			@Test
			@DisplayName("Agregar actor correcto")
			void test1(){
				var test = new Actor(0,"Juan","Carlos");
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
			@ParameterizedTest(name = "Caso {index}: {0} - {1} - {2}")
			@DisplayName("Datos incorrectos, el actor no es valido")
			@CsvSource(value = {"1, ,Carlos","1,Carlos, ", "1,'',Carlos","1,carlos,''", 
					"1, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, Carlos", 
					"1, Carlos, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					"1, a, Carlos", "1, Carlos, a", "1,'     ',Carlos", "1, Carlos, '      '"})
			void test1(int id, String name, String surname){
				var test = new Actor(id,name,surname);
				assertThrows(InvalidDataException.class, () -> srv.add(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, el actor es null")
			void test2() {
				Actor test = null;
				assertThrows(InvalidDataException.class, () -> srv.add(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, ya esta añadido")
			void test3() {
				var test = new Actor(1,"Juan","Carlos");
				when(dao.existsById(anyInt())).thenReturn(true);
				assertThrows(DuplicateKeyException.class, () -> srv.add(test));
			}
			
		}
	}
	
	
	@Nested
	@DisplayName("Pruebas de modificar actores")
	class Modify{
		@Nested
		class OK{
			@Test
			@DisplayName("Agregar actor correcto")
			void test1(){
				var test = new Actor(1,"Juan","Carlos");
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
			@ParameterizedTest(name = "Caso {index}: {0} - {1} - {2}")
			@DisplayName("Datos incorrectos, el actor no es valido")
			@CsvSource(value = {"1, ,Carlos","1,Carlos, ", "1,'',Carlos","1,carlos,''", 
					"1, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, Carlos", 
					"1, Carlos, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
					"1, a, Carlos", "1, Carlos, a", "1,'     ',Carlos", "1, Carlos, '      '"})
			void test1(int id, String name, String surname){
				var test = new Actor(id,name,surname);
				assertThrows(InvalidDataException.class, () -> srv.modify(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, el actor es null")
			void test2() {
				Actor test = null;
				assertThrows(InvalidDataException.class, () -> srv.modify(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos,no esta añadido id distito de 0")
			void test3() {
				var test = new Actor(1,"Juan","Carlos");
				when(dao.existsById(anyInt())).thenReturn(false);
				assertThrows(NotFoundException.class, () -> srv.modify(test));
			}
			
			@Test
			@DisplayName("Datos incorrectos, no esta añadido id 0")
			void test4() {
				var test = new Actor(0,"Juan","Carlos");
				assertThrows(NotFoundException.class, () -> srv.modify(test));
			}
			
		}
	}
	
	
	@Nested
	@DisplayName("Pruebas de elimirar actores")
	class Delete{
		@Nested
		class OK{
			@Test
			@DisplayName("Eliminar actor correcto")
			void test1(){
				var test = new Actor(1,"Juan","Carlos");
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
			@DisplayName("Datos incorrectos, el actor es null")
			void test2() {
				Actor test = null;
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
            var expected = Optional.of(new Actor(1));
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
            var expected = List.of(new Actor(1), new Actor(2));
            when(dao.findAll()).thenReturn(expected);
            var result = srv.getAll();
            assertEquals(expected, result);
        }

        @Test
        @DisplayName("Obtener todos con Sort correcto")
        void test2() {
            var expected = List.of(new Actor(1), new Actor(2));
            var sort = Sort.by("title");
            when(dao.findAll(sort)).thenReturn(expected);
            var result = srv.getAll(sort);
            assertEquals(expected, result);
        }

		@Test
        @DisplayName("Obtener todos con Pageable correcto")
        void test3() {
            Page<Actor> expected = mock(Page.class);
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