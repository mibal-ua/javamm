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
import static org.mockito.Mockito.when;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ExpressionContextImpl_Constructor_UnitTest {

    @Mock
    private ExpressionEvaluator<Expression> evaluator1;

    @Mock
    private ExpressionEvaluator<Expression> evaluator2;

    @Mock
    private ExpressionUpdater<UpdatableExpression> updater1;

    @Mock
    private ExpressionUpdater<UpdatableExpression> updater2;

    @Test
    @Order(1)
    void Should_throw_ConfigException_if_duplicate_of_expression_evaluator_is_found() {
        when(evaluator1.getExpressionClass()).thenReturn(Expression.class);
        when(evaluator1.toString()).thenReturn("evaluator");
        when(evaluator2.getExpressionClass()).thenReturn(Expression.class);
        when(evaluator2.toString()).thenReturn("evaluator");

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new ExpressionContextImpl(of(evaluator1, evaluator2), of()));
        assertEquals("Duplicate of ExpressionEvaluator found: " +
                "expression=ua.mibal.javamm.code.fragment.Expression, " +
                "evaluator1=evaluator, evaluator2=evaluator", exception.getMessage());
    }

    @Test
    @Order(2)
    void Should_throw_ConfigException_if_duplicate_of_expression_updater_is_found() {
        when(updater1.getExpressionClass()).thenReturn(UpdatableExpression.class);
        when(updater1.toString()).thenReturn("updater");
        when(updater2.getExpressionClass()).thenReturn(UpdatableExpression.class);
        when(updater2.toString()).thenReturn("updater");

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new ExpressionContextImpl(of(), of(updater1, updater2)));
        assertEquals("Duplicate of ExpressionUpdater found: " +
                "expression=ua.mibal.javamm.code.fragment.UpdatableExpression, " +
                "updater1=updater, updater2=updater", exception.getMessage());
    }
}
