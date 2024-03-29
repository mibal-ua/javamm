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

package ua.mibal.javamm.compiler.component.impl;

import ua.mibal.javamm.code.fragment.Expression;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.compiler.component.ExpressionBuilder;
import ua.mibal.javamm.compiler.component.ExpressionResolver;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.lang.String.join;

/**
 * @author Mykhailo Balakhon
 * @link https://t.me/mibal_ua
 */
public final class ExpressionResolverImpl implements ExpressionResolver {

    private final Collection<ExpressionBuilder> expressionBuilders;

    public ExpressionResolverImpl(final Set<ExpressionBuilder> expressionBuilders) {
        this.expressionBuilders = List.copyOf(expressionBuilders);
    }

    @Override
    public Expression resolve(final List<String> expressionTokens, final SourceLine sourceLine) {
        for (final ExpressionBuilder expressionBuilder : expressionBuilders) {
            if (expressionBuilder.canBuild(expressionTokens)) {
                return expressionBuilder.build(expressionTokens, sourceLine);
            }
        }
        // FIXME Can be complex expression
        throw new JavammLineSyntaxError(format(
                "Unsupported expression: '%s'", join(" ", expressionTokens)
        ), sourceLine);
    }

    /*
    //Functional
    @Override
    public Expression resolve(final List<String> expressionTokens, final SourceLine sourceLine) {
        return expressionBuilders
                .stream()
                .filter(expressionBuilder -> expressionBuilder.canBuild(expressionTokens))
                .findFirst()
                .map(expressionBuilder -> expressionBuilder.build(expressionTokens, sourceLine))
                .orElseThrow(() -> {
                    // FIXME Can be complex expression
                    throw new JavammLineSyntaxError("Unsupported expression: " + join("", expressionTokens), sourceLine);
                });
    }*/
}
