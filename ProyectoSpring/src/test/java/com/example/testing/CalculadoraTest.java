package com.example.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas de la clase Calculadora")
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
			
			@Test
			@DisplayName("Suma de dos enteros")
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition} / {totalRepetitions}")
			void testAdd() {
				var calculation = cal.add(2, 2);
				assertEquals(4, calculation);
				assertEquals(3, cal.add(4,-1));
				assertEquals(-5, cal.add(-4,-1),"caso -4, -2");
				assertEquals(3, cal.add(4,-1));
				assertEquals(0, cal.add(0,0));


			}
			@Test
			@DisplayName("Suma negativa de dos reales")
			void testAddRealValues() {
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
