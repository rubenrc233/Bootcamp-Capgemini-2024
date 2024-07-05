//package com.example.domains.entities;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.math.BigDecimal;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//
//class FilmTest {
//
//    @Nested
//    @DisplayName("Validaciones de atributos")
//    class Validations {
//        @Nested
//        class OK {
//            @Test
//            @DisplayName("Datos correctos")
//            void test1() {
//                var test = new Film(1, 120, "R", (byte) 3, new BigDecimal("4.99"), new BigDecimal("19.99"));
//                test.setTitle("Inception");
//                test.setLanguage(new Language(1));
//                assertTrue(test.isValid());
//            }
//        }
//
//        @Nested
//        class KO {
//            @ParameterizedTest(name = "Caso {index}: {0} - {1} - {2} - {3} - {4} - {5} - {6}")
//            @DisplayName("Datos incorrectos")
//            @CsvSource({
//                "1, 120, R, 3, 4.99, 19.99, ''", // Title vacío
//                "1, 120, R, 3, 4.99, 19.99, 'A'", // Title muy corto
//                "1, 120, R, 3, 4.99, 19.99, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'", // Title muy largo
//                "1, -1, R, 3, 4.99, 19.99, 'Inception'", // Length negativo
//                "1, 120, R, -1, 4.99, 19.99, 'Inception'", // RentalDuration negativo
//            })
//            void test1(int filmId, int length, String rating, byte rentalDuration, BigDecimal rentalRate, BigDecimal replacementCost, String title) {
//                var test = new Film(filmId, length, rating, rentalDuration, rentalRate, replacementCost);
//                test.setTitle(title);
//                test.setLanguage(new Language(1));
//                assertTrue(test.isInvalid());
//            }
//            @Test
//            @DisplayName("Language incorrecto")
//            void test2() {
//                var test = new Film(1, 120, "R", (byte) 3, new BigDecimal("4.99"), new BigDecimal("19.99"));
//                test.setTitle("Inception");
//                test.setLanguage(null);
//                assertTrue(test.isInvalid());
//            }
//        }
//    }
//}