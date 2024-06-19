package com.example.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
		}
		
		@Nested
		class Wrong{
			
		}
	}


}
