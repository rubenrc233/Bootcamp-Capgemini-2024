package com.example.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

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

	@Test
	void testAdd() {
		var cal = new Calculadora();
		var calculation = cal.add(2, 2);
		assertEquals(4, calculation);
	}
	@Test
	void testAddRealValues() {
		var cal = new Calculadora();
		var calculation = cal.add(1, -0.9);
		assertEquals(0.1, calculation);
	}
	
	@Test
	void testDiv() {
		var cal = new Calculadora();
		var calculation = cal.div(3.0, 2.0);
		assertEquals(1.5, calculation);
	}
	@Test
	void testDivError() {
		var cal = new Calculadora();
		assertThrows(ArithmeticException.class, () -> cal.div(3.0,0));
	}

}
