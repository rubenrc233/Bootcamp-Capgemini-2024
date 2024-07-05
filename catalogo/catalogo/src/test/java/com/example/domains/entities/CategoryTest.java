package com.example.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

    @Nested
    @DisplayName("Validaciones de atributos")
    class Validations {
        @Nested
        class OK {
            @Test
            @DisplayName("Datos correctos")
            void test1() {
                var test = new Category(1, "Action");
                assertTrue(test.isValid());
            }
        }

        @Nested
        class KO {
            @ParameterizedTest(name = "Caso {index}: {0} - {1}")
            @DisplayName("Datos incorrectos")
            @CsvSource({
                "1, ''", // Name vacío
                "1, 'A'", // Name muy corto
                "1, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'", // Name muy largo (más de 25 caracteres)
                "1, ' '", // Name con espacios solamente
                "1, '                       '" // Name con muchos espacios
            })
            void test1(int categoryId, String name) {
                var test = new Category(categoryId, name);
                assertTrue(test.isInvalid());
            }
        }
    }

}
