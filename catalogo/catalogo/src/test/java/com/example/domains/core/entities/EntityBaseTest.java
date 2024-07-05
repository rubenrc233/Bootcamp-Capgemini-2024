package com.example.domains.core.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

class EntityBaseTest {
	
	class Dummy extends EntityBase<Dummy>{
		@PositiveOrZero
		int id;
		
		@Size(max = 4, min = 2)
		@NotBlank
		String name;

		public Dummy( int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}
		
		
	}
	
	@Nested
	@DisplayName("Validaciones de atributos")
	class ProductosNormales{
		@Nested
		class OK{
			@Test
			@DisplayName("Datos correctos")
			void test1(){
				var test = new Dummy(1,"Juan");
				assertTrue(test.isValid());
			}

		}
		@Nested
		class KO{
			@ParameterizedTest(name = "Caso {index}: {0} - {1} - {2}")
			@DisplayName("Datos incorrectos")
			@CsvSource(value = {"1,C","-1,Carl","1,Carlos", "1,''","1,'     '", "1, "})
			void test1(int id, String name){
				var test = new Dummy(id,name);
				test.isInvalid();
				assertAll("Entity",() -> assertTrue(test.isInvalid()),
						()-> assertTrue(!test.getErrorsFields().isEmpty()), 
						()->assertTrue(!test.getErrorsMessage().isBlank()));
			}
			
		}
	}

}
