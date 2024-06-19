package com.example.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Pruebas de la clase Persona")
class PersonaTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("Pruebas del proceso de instanciacion")
	class Create{
		
		@Nested
		class OK{
			
			@Test
			@DisplayName("Solo se indica el nombre")
			void soloNombre() {
				var p = new Persona(1,"Pepe");
				assertNotNull(p);
				assertAll("Persona",
				()-> assertEquals(1, p.getId(),"Comprueba el ID"),
				() -> assertEquals("Pepe", p.getNombre(),"Comprueba el nombre"),
				() -> assertTrue(p.getApellidos().isEmpty(),"Comprueba los apellidos"));
			}
			
			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = {"1,Pepe","2,Jose"})
			@DisplayName("Solo se indica el nombre (parametrizado)")
			void soloNombreParametrizado(ArgumentsAccessor args) {
				var p = new Persona(args.getInteger(0),args.getString(1));
				assertNotNull(p);
				assertAll("Persona",
				()-> assertEquals(args.getInteger(0), p.getId(),"Comprueba el ID"),
				() -> assertEquals(args.getString(1), p.getNombre(),"Comprueba el nombre"),
				() -> assertTrue(p.getApellidos().isEmpty(),"Comprueba los apellidos"));
			}
		}
		
		@Nested
		class Wrong{
			@ParameterizedTest(name = "{0} {1}")
			@CsvSource(value = {"4,","5,'      '","6,''"})
			@DisplayName("Solo se indica el nombre en formatos no aceptado(parametrizado)")
			void soloNombreParametrizado(int id, String nombre) {
				assertThrows(Exception.class, () -> new Persona(id, nombre));
			}
		}
	}


}
