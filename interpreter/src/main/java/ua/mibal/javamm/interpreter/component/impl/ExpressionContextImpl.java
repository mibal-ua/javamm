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

import ua.mibal.javamm.code.component.ExpressionContext;
import ua.mibal.javamm.code.exception.ConfigException;
import ua.mibal.javamm.code.fragment.Expression;
import ua.mibal.javamm.code.fragment.UpdatableExpression;
import ua.mibal.javamm.interpreter.component.ExpressionEvaluator;
import ua.mibal.javamm.interpreter.component.ExpressionUpdater;

import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public final class ExpressionContextImpl implements ExpressionContext {

    private final Map<Class<? extends Expression>, ExpressionEvaluator> expressionEvaluatorMap;

    private final Map<Class<? extends UpdatableExpression>, ExpressionUpdater> expressionUpdaterMap;

    public ExpressionContextImpl(final Set<ExpressionEvaluator<?>> expressionEvaluators,
                                 final Set<ExpressionUpdater<?>> expressionUpdaters) {
        this.expressionEvaluatorMap = expressionEvaluators.stream()
                .collect(toUnmodifiableMap(ExpressionEvaluator::getExpressionClass, identity(),
                        checkExpressionEvaluatorDuplicates()));
        this.expressionUpdaterMap = expressionUpdaters.stream()
                .collect(toUnmodifiableMap(ExpressionUpdater::getExpressionClass, identity(),
                        checkExpressionUpdaterDuplicates()));
    }

    private BinaryOperator<ExpressionEvaluator> checkExpressionEvaluatorDuplicates() {
        return (oi1, oi2) -> {
            throw new ConfigException(format(
                    "Duplicate of ExpressionEvaluator found: expression=%s, evaluator1=%s, evaluator2=%s",
                    oi1.getExpressionClass().getName(), oi1, oi2));
        };
    }

    private BinaryOperator<ExpressionUpdater> checkExpressionUpdaterDuplicates() {
        return (oi1, oi2) -> {
            throw new ConfigException(format(
                    "Duplicate of ExpressionUpdater found: expression=%s, updater1=%s, updater2=%s",
                    oi1.getExpressionClass().getName(), oi1, oi2));
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getValue(final Expression expression) {
        final ExpressionEvaluator expressionEvaluator = expressionEvaluatorMap.get(expression.getClass());
        if (expressionEvaluator == null) {
            throw new ConfigException("ExpressionEvaluator not defined for " + expression.getClass());
        } else {
            return expressionEvaluator.evaluate(expression);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setValue(final UpdatableExpression updatableExpression,
                         final Object updatedValue) {
        final ExpressionUpdater expressionUpdater = expressionUpdaterMap.get(updatableExpression.getClass());
        if (expressionUpdater == null) {
            throw new ConfigException("ExpressionUpdater not defined for " + updatableExpression.getClass());
        } else {
            expressionUpdater.update(updatableExpression, updatedValue);
        }
    }

    /*@SuppressWarnings("unchecked")
    //Functional
    public Object getValue(final Expression expression) {
        return ofNullable(expressionEvaluatorMap.get(expression.getClass()))
                .orElseThrow(exceptionSupplier(expression.getClass(), "ExpressionEvaluator"))
                .evaluate(expression);
    }
    @SuppressWarnings("unchecked")
    //Functional
    public void setValue(final UpdatableExpression updatableExpression,
                         final Object updatedValue) {
        ofNullable(expressionUpdaterMap.get(updatableExpression.getClass()))
                .orElseThrow(exceptionSupplier(updatableExpression.getClass(), "ExpressionUpdater"))
                .update(updatableExpression, updatedValue);
    }
    private Supplier<RuntimeException> exceptionSupplier(final Class<?> expressionClass, final String handlerName) {
        return () -> {
            throw new ConfigException(format("%s not defined for %s",
                    handlerName, expressionClass));
        };
    }*/
}
