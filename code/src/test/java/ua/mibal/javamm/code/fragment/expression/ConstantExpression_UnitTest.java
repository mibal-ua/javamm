/*
 * Copyright (c) 2023. http://t.me/mibal_ua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ua.mibal.javamm.code.fragment.expression;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.component.ExpressionContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ConstantExpression_UnitTest {

    @Mock
    private ExpressionContext expressionContext;

    @Test
    @Order(1)
    void Constructor_should_throw_IllegalArgumentException_if_value_is_null() {
        final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> ConstantExpression.valueOf(null)
        );
        assertEquals(
                "null value is not allowed. Use NullValueExpression instead",
                e.getMessage()
        );
    }

    @Test
    @Order(2)
    void Constructor_should_throw_IllegalArgumentException_if_value_is_unsupported() {
        final IllegalArgumentException e2 = assertThrows(IllegalArgumentException.class,
                () -> ConstantExpression.valueOf(new ConstantExpression_UnitTest())
        );
        assertEquals(
                "Unsupported value type: " + ConstantExpression_UnitTest.class.getName(),
                e2.getMessage()
        );
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource({"true", "false"})
    void valueOf_should_return_static_instances_for_boolean_types(final Boolean value) {
        assertSame(
                ConstantExpression.valueOf(value),
                ConstantExpression.valueOf(value)
        );
    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(ints = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void valueOf_should_return_static_instances_from_integer_pool(final Integer value) {
        assertSame(
                ConstantExpression.valueOf(value),
                ConstantExpression.valueOf(value)
        );
    }

    @ParameterizedTest
    @Order(5)
    @ValueSource(ints = {-100, -10, -5, -2, 11, 15, 50, 100, 1000})
    void valueOf_should_create_new_instances_for_other_values(final Integer value) {
        assertNotSame(
                ConstantExpression.valueOf(value),
                ConstantExpression.valueOf(value)
        );
    }

    @Test
    @Order(6)
    void valueOf_should_support_all_javamm_types() {
        Object[] values = {
                true, false,
                1, 2, 3, 1000, Integer.MAX_VALUE, Integer.MIN_VALUE,
                1.1, 2.000005, 1000, Double.MAX_VALUE, Double.MIN_VALUE,
                "", " ", "123", "Hello Javamm"
                //TODO more new supported classes
        };
        for (final Object correctValue : values) {
            assertDoesNotThrow(() -> {
                ConstantExpression.valueOf(correctValue);
            });
            assertSame(
                    correctValue,
                    ConstantExpression.valueOf(correctValue).getValue(null)
            );
        }
    }

    @Test
    @Order(7)
    void getValue_should_not_invoke_getValue_from_expressionContext() {
        Object[] values = {
                true, false,
                1, 2, 3, 1000, Integer.MAX_VALUE, Integer.MIN_VALUE,
                1.1, 2.000005, 1000, Double.MAX_VALUE, Double.MIN_VALUE,
                "", " ", "123", "Hello Javamm"
                //TODO more new supported classes
        };
        for (final Object value : values) {
            final ConstantExpression expression = ConstantExpression.valueOf(value);

            assertEquals(value, expression.getValue(expressionContext));
            verify(expressionContext, never()).getValue(expression);
        }
    }

    @Test
    @Order(8)
    void toString_should_return_value_toString() {
        Object[] values = {
                true, false,
                1, 2, 3, 1000, Integer.MAX_VALUE, Integer.MIN_VALUE,
                1.1, 2.000005, 1000, Double.MAX_VALUE, Double.MIN_VALUE,
                "", " ", "123", "Hello Javamm"
                //TODO more new supported classes
        };
        for (final Object value : values) {
            final ConstantExpression expression = ConstantExpression.valueOf(value);

            assertEquals(
                    value.toString(),
                    expression.toString()
            );
        }
    }
}
