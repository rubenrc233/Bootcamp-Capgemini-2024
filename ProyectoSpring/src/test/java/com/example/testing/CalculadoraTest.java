package com.example.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Pruebas de la clase Calculadora")
@TestMethodOrder(MethodOrderer.class)
class CalculadoraTest {

	Calculadora cal;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		cal = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Metodo de suma")
	class Add{
		
		@Nested
		class Ok{
			
			@ParameterizedTest(name = "Caso {index} : {0} + {1} = {2}")
			@DisplayName("Suma de dos enteros")
			@CsvSource(value = {"1,2,3","3,-1,2","-1,2,1","-1,-1,-2","0,1,1","0.1,0.2,0.3"})
			void testAdd(double operando1, double operando2, double res) {
				assertEquals(res, cal.add(operando1,operando2));
			}
			
			@Test
			@DisplayName("Suma negativa de dos reales")
			void testAddRealValues() {
				var calculation = cal.add(1, -0.9);
				assertEquals(0.1, calculation);
			}
			
			
			@Test
			@DisplayName("Suma negativa de dos reales")
			@Smoke
			void testAddTag() {
				var calculation = cal.add(1, -0.9);
				assertEquals(0.1, calculation);
			}
		}
		
		@Nested
		class Wrong{
			
		}
	}

	@Nested
	@DisplayName("Metodo de division")
	class Div{
		
		@Nested
		class Ok{
			
			@Test
			@DisplayName("Division positiva de dos reales")
			void testDiv() {
				var calculation = cal.div(3.0, 2.0);
				assertEquals(1.5, calculation);
			}
		}
		
		@Nested
		class Wrong{
			
			@Test
			@DisplayName("Division entre 0")
			void testDivError() {
				assertThrows(ArithmeticException.class, () -> cal.div(3.0,0));
			}
		}
	}



}
