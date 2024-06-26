package com.example.domains.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Nested
    @DisplayName("Validaciones de atributos")
    class ValidacionesDeAtributos {

        @Nested
        @DisplayName("ID de Categoría")
        class IdDeCategoria {

            @Test
            @DisplayName("ID correcto")
            void testIdCorrecto() {
                Category category = new Category();
                category.setCategoryId(1);
                assertEquals(1, category.getCategoryId());
            }

            @Test
            @DisplayName("ID incorrecto")
            void testIdIncorrecto() {
                Category category = new Category();
                category.setCategoryId(-1);
                assertNotEquals(1, category.getCategoryId());
            }
        }

        @Nested
        @DisplayName("Nombre de Categoría")
        class NombreDeCategoria {

            @Test
            @DisplayName("Nombre correcto")
            void testNombreCorrecto() {
                Category category = new Category();
                category.setName("Drama");
                assertEquals("Drama", category.getName());
            }

        @Nested
        @DisplayName("Última actualización")
        class UltimaActualizacion {

            @Test
            @DisplayName("Fecha correcta")
            void testFechaCorrecta() {
                Category category = new Category();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                category.setLastUpdate(timestamp);
                assertEquals(timestamp, category.getLastUpdate());
            }

            @Test
            @DisplayName("Fecha incorrecta")
            void testFechaIncorrecta() {
                Category category = new Category();
                Timestamp futureTimestamp = new Timestamp(System.currentTimeMillis() + 100000);
                category.setLastUpdate(futureTimestamp);
                assertTrue(category.getLastUpdate().after(new Timestamp(System.currentTimeMillis())));
            }
        }
    }

    @Nested
    @DisplayName("Operaciones con FilmCategories")
    class OperacionesConFilmCategories {

        @Test
        @DisplayName("Agregar FilmCategory")
        void testAgregarFilmCategory() {
            Category category = new Category();
            FilmCategory filmCategory = new FilmCategory();
            category.setFilmCategories(new ArrayList<>());
            category.addFilmCategory(filmCategory);

            assertTrue(category.getFilmCategories().contains(filmCategory));
            assertEquals(category, filmCategory.getCategory());
        }

        @Test
        @DisplayName("Remover FilmCategory")
        void testRemoverFilmCategory() {
            Category category = new Category();
            FilmCategory filmCategory = new FilmCategory();
            category.setFilmCategories(new ArrayList<>());
            category.addFilmCategory(filmCategory);
            category.removeFilmCategory(filmCategory);

            assertFalse(category.getFilmCategories().contains(filmCategory));
            assertNull(filmCategory.getCategory());
        }
    }

    @Nested
    @DisplayName("Métodos Equals y HashCode")
    class MetodosEqualsYHashCode {

        @Test
        @DisplayName("Equals y HashCode correctos")
        void testEqualsYHashCodeCorrectos() {
            Category category1 = new Category(1);
            Category category2 = new Category(1);
            Category category3 = new Category(2);

            assertEquals(category1, category2);
            assertNotEquals(category1, category3);
            assertEquals(category1.hashCode(), category2.hashCode());
            assertNotEquals(category1.hashCode(), category3.hashCode());
        }
    }

    @Nested
    @DisplayName("Método ToString")
    class MetodoToString {

        @Test
        @DisplayName("ToString correcto")
        void testToStringCorrecto() {
            Category category = new Category();
            category.setCategoryId(1);
            category.setName("Drama");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            category.setLastUpdate(timestamp);

            String expected = "Category [categoryId=1, name=Drama, lastUpdate=" + timestamp + "]";
            assertEquals(expected, category.toString());
        }
    }
   }
 }
