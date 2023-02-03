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
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.component.ExpressionContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static ua.mibal.javamm.code.fragment.expression.TypeExpression.is;
import static ua.mibal.javamm.code.fragment.expression.TypeExpression.of;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class TypeExpression_UnitTest {

    @Mock
    private ExpressionContext context;

    @ParameterizedTest
    @Order(1)
    @EnumSource(TypeExpression.class)
    void getKeyword_should_return_lowerCase(final TypeExpression expression) {
        assertEquals(
                expression.name().toLowerCase(),
                expression.getKeyword()
        );
    }

    @ParameterizedTest
    @Order(2)
    @EnumSource(TypeExpression.class)
    void is_should_return_true(final TypeExpression expression) {
        assertTrue(is(expression.getKeyword()));
    }

    @ParameterizedTest
    @Order(3)
    @EnumSource(TypeExpression.class)
    void of_should_return_correct_type(final TypeExpression expression) {
        assertSame(expression, of(expression.getKeyword()));
    }

    @Test
    @Order(4)
    void of_should_throw_IllegalArgumentException_for_unsupported_type() {
        final IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> of("IncorrectType"));
        assertEquals(
                "Unsupported type: IncorrectType",
                e.getMessage()
        );
    }

    @ParameterizedTest
    @Order(5)
    @EnumSource(TypeExpression.class)
    void getValue_should_return_the_same_object_without_interaction_with_context(final TypeExpression expression) {
        assertSame(expression, expression.getValue(context));
        verify(context, never()).getValue(any());
    }

    @ParameterizedTest
    @Order(6)
    @EnumSource(TypeExpression.class)
    void getType_should_return_correct_type_instance(final TypeExpression expression) throws ClassNotFoundException {
        final StringBuilder className = new StringBuilder(
                "java.lang." + expression.name().charAt(0) + expression.name().toLowerCase()); // "java.lang." + "I" + "integer"
        className.deleteCharAt(11); // java.lang.Integer (delete 'i')
        assertSame(
                Class.forName(className.toString()),
                expression.getType()
        );
    }

    @ParameterizedTest
    @Order(7)
    @EnumSource(TypeExpression.class)
    void toString_should_return_lower_cased_type_name(final TypeExpression expression) {
        assertEquals(expression.getKeyword(), expression.toString());
    }
}
