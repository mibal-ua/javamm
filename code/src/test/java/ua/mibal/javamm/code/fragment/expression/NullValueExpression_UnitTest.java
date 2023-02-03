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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.component.ExpressionContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class NullValueExpression_UnitTest {

    @Mock
    private ExpressionContext context;

    @Test
    @Order(1)
    void getInstance_should_return_singleton_instance() {
        assertSame(
                NullValueExpression.getInstance(),
                NullValueExpression.getInstance()
        );
    }

    @Test
    @Order(2)
    void getValue_should_return_null_without_interaction_with_context() {
        final NullValueExpression expression = NullValueExpression.getInstance();

        assertNull(expression.getValue(context));
        verify(context, never()).getValue(any());
    }

    @Test
    @Order(3)
    void toString_should_return_null() {
        assertEquals("null",
                NullValueExpression.getInstance().toString());
    }
}
