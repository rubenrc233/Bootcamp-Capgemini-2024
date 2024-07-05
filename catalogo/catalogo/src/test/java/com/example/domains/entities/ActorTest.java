package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ActorTest {

	 @Nested
		@DisplayName("Validaciones de atributos")
		class ProductosNormales{
			@Nested
			class OK{
				@Test
				@DisplayName("Datos correctos")
				void test1(){
					var test = new Actor(1,"Juan","Carlos");
					assertTrue(test.isValid());
				}

			}
			@Nested
			class KO{
				@ParameterizedTest(name = "Caso {index}: {0} - {1} - {2}")
				@DisplayName("Datos incorrectos")
				@CsvSource(value = {"1, ,Carlos","1,Carlos, ", "1,'',Carlos","1,carlos,''", 
						"1, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, Carlos", 
						"1, Carlos, aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
						"1, a, Carlos", "1, Carlos, a", "1,'     ',Carlos", "1, Carlos, '      '"})
				void test1(int id, String name, String surname){
					var test = new Actor(id,name,surname);
					assertTrue(test.isInvalid());
				}
				
			}
		}

}
