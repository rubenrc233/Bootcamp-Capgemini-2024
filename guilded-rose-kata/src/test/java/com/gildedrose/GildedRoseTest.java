package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("Pruebas de la funcionalidad del GildedRose")
class GildedRoseTest {
	GildedRose gr;
	Item[] it;
	
	@BeforeEach
	void setUp() throws Exception {
		gr = null;
		it = new Item[1];
	}
	@Nested
	@DisplayName("Pruebas para la funcionalidad de los items generales")
	class GeneralItemTest{
		@Nested
		class CasosPositivos{
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items generales cuando la calidad y la fecha son > 0")
			@CsvSource(value = {"'patata',1,9"})
			void testGeneral(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad -1, it[0].quality);
				assertEquals(fecha -1, it[0].sellIn);
			}
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items generales cuando la calidad es 0 o menos")
			@CsvSource(value = {"'patata',1,0"})
			void testCalidadMenor0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad, it[0].quality);
				assertEquals(fecha -1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items generales cuando la fecha es menor que 0")
			@CsvSource(value = {"'patata',0,10"})
			void testfechaMenor0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad - 2, it[0].quality);
				assertEquals(fecha- 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items generales cuando la fecha es menor que 0 y la calidad no puede ser <0")
			@CsvSource(value = {"'patata',0,1"})
			void testfechaMenor0CalidadNoMenor(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad-1, it[0].quality);
				assertEquals(fecha- 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items generales cuando la calidad y la fecha son > 0")
			@CsvSource(value = {"'patata',1,9"})
			void testToString(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				assertEquals(nombre + ", " + fecha + ", " + calidad, it[0].toString());
			}
		}
	}
	@Nested
	@DisplayName("Pruebas para la funcionalidad de los items conjurados")
	class ConjuradoItemTest{
		@Nested
		class CasosPositivos{
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items conjurados cuando la calidad y la fecha son > 0")
			@CsvSource(value = {"'Conjurado patata',1,9"})
			void testGeneral(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad - (1*2), it[0].quality);
				assertEquals(fecha -1, it[0].sellIn);
			}
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items conjurados cuando la calidad es 0 o menos")
			@CsvSource(value = {"'Conjurado patata',1,0"})
			void testCalidadMenor0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad, it[0].quality);
				assertEquals(fecha -1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items conjurados cuando la fecha es menor que 0")
			@CsvSource(value = {"'Conjurado',0,10"})
			void testfechaMenor0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad - (2*2), it[0].quality);
				assertEquals(fecha- 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items conjurados cuando la fecha es menor que 0 y la calidad no puede ser <0")
			@CsvSource(value = {"'Conjurado patata',0,1"})
			void testfechaMenor0CalidadNoMenor(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad-(1*2), it[0].quality);
				assertEquals(fecha- 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para items conjurados cuando la calidad y la fecha son > 0")
			@CsvSource(value = {"'Conjurado patata',1,9"})
			void testToString(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				assertEquals(nombre + ", " + fecha + ", " + calidad, it[0].toString());
			}
		}
	}
	
	@Nested
	@DisplayName("Pruebas para la funcionalidad de AgedBrie")
	class AgedBrieTest{
		@Nested
		class CasosPositivos{
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("Aged Brie con fecha de caducidad mayor que 0")
			@CsvSource(value = {"'Aged Brie',1,1"})
			void testFechaMayor0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad + 1, it[0].quality);
				assertEquals(fecha - 1, it[0].sellIn);

			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("Aged Brie con fecha de caducidad igual o menor que 0")
			@CsvSource(value = {"'Aged Brie',0,1"})
			void testFechaMenorIgual0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad + 2, it[0].quality);
				assertEquals(fecha -1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("Aged Brie con calidad 50 o mayor")
			@CsvSource(value = {"'Aged Brie',0,50","'Aged Brie',0,55"})
			void testCalidadMayorIgual50(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad, it[0].quality);
				assertEquals(fecha -1, it[0].sellIn);

			}
		}
	}
	
	@Nested
	@DisplayName("Pruebas para la funcionalidad de Sulfuras")
	class SulfurasTest{
		@Nested
		class CasosPositivos{
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para Sulfuras (nada cambia)")
			@CsvSource(value = {"'Sulfuras, Hand of Ragnaros',1,55"})
			void testGeneral(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad, it[0].quality);
				assertEquals(fecha, it[0].sellIn);
			}
		}
	}
	
	@Nested
	@DisplayName("Pruebas para la funcionalidad de Backstage")
	class BackstageTest{
		@Nested
		class CasosPositivos{
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para Backstage fecha mayor o igual a 10")
			@CsvSource(value = {"'Backstage passes to a TAFKAL80ETC concert',11,1"})
			void testFechaMayor10(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad + 1, it[0].quality);
				assertEquals(fecha - 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para Backstage 5 < fecha <= 5")
			@CsvSource(value = {"'Backstage passes to a TAFKAL80ETC concert',10,1"})
			void testFechaEntre10Y5(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad + 2, it[0].quality);
				assertEquals(fecha - 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para Backstage 0 < fecha <= 5")
			@CsvSource(value = {"'Backstage passes to a TAFKAL80ETC concert',5,1","'Backstage passes to a TAFKAL80ETC concert',3,1"})
			void testFechaMenor5(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(calidad + 3, it[0].quality);
				assertEquals(fecha - 1, it[0].sellIn);
			}
			
			@ParameterizedTest(name = "Caso {index} : {0} con fecha {1} y calidad {2}")
			@DisplayName("test para Backstage 0 < fecha <= 5")
			@CsvSource(value = {"'Backstage passes to a TAFKAL80ETC concert',0,1","'Backstage passes to a TAFKAL80ETC concert',0,-1"})
			void testFechaMenor0(String nombre, int fecha, int calidad) {
				it[0] = new Item(nombre, fecha, calidad);
				gr = new GildedRose(it);
				gr.updateQuality();
				assertEquals(nombre, it[0].name);
				assertEquals(0, it[0].quality);
				assertEquals(fecha - 1, it[0].sellIn);
			}
		}
	}
}
