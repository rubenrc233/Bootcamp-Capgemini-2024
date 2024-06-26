package com.example.domains.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilmTest {

    @Nested
    @DisplayName("Validaciones de atributos")
    class ValidacionesDeAtributos {

        @Nested
        @DisplayName("ID de Film")
        class IdDeFilm {

            @Test
            @DisplayName("ID correcto")
            void testIdCorrecto() {
                Film film = new Film();
                film.setFilmId(1);
                assertEquals(1, film.getFilmId());
            }

            @Test
            @DisplayName("ID incorrecto")
            void testIdIncorrecto() {
                Film film = new Film();
                film.setFilmId(-1);
                assertNotEquals(1, film.getFilmId());
            }
        }

        @Nested
        @DisplayName("Título de Film")
        class TituloDeFilm {

            @Test
            @DisplayName("Título correcto")
            void testTituloCorrecto() {
                Film film = new Film();
                film.setTitle("Inception");
                assertEquals("Inception", film.getTitle());
            }
        }

        @Nested
        @DisplayName("Duración del alquiler")
        class DuracionDelAlquiler {

            @Test
            @DisplayName("Duración correcta")
            void testDuracionCorrecta() {
                Film film = new Film();
                film.setRentalDuration((byte) 5);
                assertEquals(5, film.getRentalDuration());
            }
        }

        @Nested
        @DisplayName("Tasa de alquiler")
        class TasaDeAlquiler {

            @Test
            @DisplayName("Tasa correcta")
            void testTasaCorrecta() {
                Film film = new Film();
                film.setRentalRate(new BigDecimal("2.99"));
                assertEquals(new BigDecimal("2.99"), film.getRentalRate());
            }

            @ParameterizedTest(name = "Caso {index}: {0}")
            @DisplayName("Tasa incorrecta")
            @CsvSource({"-1.00", "10000.00"})
            void testTasaIncorrecta(BigDecimal rentalRate) {
                Film film = new Film();
                film.setRentalRate(rentalRate);
                assertFalse(rentalRate.compareTo(BigDecimal.ZERO) >= 0 && rentalRate.precision() <= 4 && rentalRate.scale() <= 2);
            }
        }
    }

    @Nested
    @DisplayName("Operaciones con FilmActors")
    class OperacionesConFilmActors {

        @Test
        @DisplayName("Agregar Actor")
        void testAgregarActor() {
            Film film = new Film();
            Actor actor = new Actor();
            film.setActors(new ArrayList<>());
            film.addActor(actor);

            assertTrue(film.getActors().contains(actor));
        }

        @Test
        @DisplayName("Remover Actor")
        void testRemoverActor() {
            Film film = new Film();
            Actor actor = new Actor();
            film.setActors(new ArrayList<>());
            film.addActor(actor);
            film.removeActor(actor);

            assertFalse(film.getActors().contains(actor));
        }
    }

    @Nested
    @DisplayName("Operaciones con FilmCategories")
    class OperacionesConFilmCategories {

        @Test
        @DisplayName("Agregar Categoría")
        void testAgregarCategoria() {
            Film film = new Film();
            Category category = new Category();
            film.setCategories(new ArrayList<>());
            film.addCategory(category);

            assertTrue(film.getCategories().contains(category));
        }

        @Test
        @DisplayName("Remover Categoría")
        void testRemoverCategoria() {
            Film film = new Film();
            Category category = new Category();
            film.setCategories(new ArrayList<>());
            film.addCategory(category);
            film.removeCategory(category);

            assertFalse(film.getCategories().contains(category));
        }
    }

    @Nested
    @DisplayName("Métodos Equals y HashCode")
    class MetodosEqualsYHashCode {

        @Test
        @DisplayName("Equals y HashCode correctos")
        void testEqualsYHashCodeCorrectos() {
            Film film1 = new Film(1);
            Film film2 = new Film(1);
            Film film3 = new Film(2);

            assertEquals(film1, film2);
            assertNotEquals(film1, film3);
            assertEquals(film1.hashCode(), film2.hashCode());
            assertNotEquals(film1.hashCode(), film3.hashCode());
        }
    }

    @Nested
    @DisplayName("Método ToString")
    class MetodoToString {

        @Test
        @DisplayName("ToString correcto")
        void testToStringCorrecto() {
            Film film = new Film();
            film.setFilmId(1);
            film.setTitle("Inception");
            film.setDescription("A mind-bending thriller");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            film.setLastUpdate(timestamp);

            String expected = "Film [filmId=1, description=A mind-bending thriller, lastUpdate=" + timestamp + ", length=0, rating=null, releaseYear=null, rentalDuration=0, rentalRate=null, replacementCost=null, title=Inception]";
            assertEquals(expected, film.toString());
        }
    }
}
