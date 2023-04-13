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

package ua.mibal.javamm.interpreter.component.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.exception.ConfigException;
import ua.mibal.javamm.code.fragment.Expression;
import ua.mibal.javamm.code.fragment.UpdatableExpression;
import ua.mibal.javamm.interpreter.component.ExpressionEvaluator;
import ua.mibal.javamm.interpreter.component.ExpressionUpdater;

import static java.util.Set.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ExpressionContextImpl_getValue_and_setValue_UnitTest {

    private ExpressionContextImpl expressionContext;

    @Mock
    private ExpressionEvaluator<TestExpression> expressionEvaluator;

    @Mock
    private ExpressionUpdater<TestUpdatableExpression> expressionUpdater;

    @BeforeEach
    void beforeEach() {
        when(expressionEvaluator.getExpressionClass()).thenReturn(TestExpression.class);
        when(expressionUpdater.getExpressionClass()).thenReturn(TestUpdatableExpression.class);
        expressionContext = new ExpressionContextImpl(of(expressionEvaluator), of(expressionUpdater));
    }

    @Test
    @Order(1)
    void getValue_should_delegate_the_call_to_the_appropriate_evaluator() {
        final TestExpression expression = new TestExpression();

        expressionContext.getValue(expression);

        verify(expressionEvaluator).evaluate(expression);
    }

    @Test
    @Order(2)
    void getValue_should_throw_ConfigException_if_expression_evaluator_is_not_defined_for_expression() {
        class TestExpressionEx extends TestExpression {

        }
        final TestExpression expression = new TestExpressionEx();

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                expressionContext.getValue(expression));
        assertEquals("ExpressionEvaluator not defined for class " +
                "ua.mibal.javamm.interpreter.component.impl.ExpressionContextImpl_getValue_and_setValue_UnitTest" +
                "$1TestExpressionEx", exception.getMessage());
        verify(expressionEvaluator, never()).evaluate(expression);
    }

    @Test
    @Order(3)
    void setValue_should_delegate_the_call_to_the_appropriate_updater() {
        final TestUpdatableExpression expression = new TestUpdatableExpression();
        final Object updatedValue = new Object();

        expressionContext.setValue(expression, updatedValue);

        verify(expressionUpdater).update(expression, updatedValue);
    }

    @Test
    @Order(4)
    void setValue_should_throw_ConfigException_if_expression_updater_is_not_defined_for_expression() {
        class TestUpdatableExpressionEx extends TestUpdatableExpression {

        }
        final TestUpdatableExpression expression = new TestUpdatableExpressionEx();

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                expressionContext.setValue(expression, null));
        assertEquals("ExpressionUpdater not defined for class " +
                "ua.mibal.javamm.interpreter.component.impl.ExpressionContextImpl_getValue_and_setValue_UnitTest" +
                "$1TestUpdatableExpressionEx", exception.getMessage());
        verify(expressionUpdater, never()).update(expression, null);
    }

    /**
     * @author Mykhailo Balakhon
     * @link t.me/mibal_ua
     */
    private static class TestExpression implements Expression {

    }

    /**
     * @author Mykhailo Balakhon
     * @link t.me/mibal_ua
     */
    private static class TestUpdatableExpression implements UpdatableExpression {

    }
}
