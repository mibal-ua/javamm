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

package ua.mibal.javamm.compiler.component.impl.operation.simple;

import ua.mibal.javamm.code.Variable;
import ua.mibal.javamm.code.fragment.Expression;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.code.fragment.expression.NullValueExpression;
import ua.mibal.javamm.code.fragment.operation.VariableDeclarationOperation;
import ua.mibal.javamm.compiler.component.ExpressionResolver;
import ua.mibal.javamm.compiler.component.VariableBuilder;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import ua.mibal.javamm.compiler.component.impl.operation.AbstractOperationReader;

import java.util.ListIterator;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static ua.mibal.javamm.code.syntax.Keywords.VAR;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class VariableDeclarationOperationReader extends AbstractOperationReader<VariableDeclarationOperation> {

    private final VariableBuilder variableBuilder;

    private final ExpressionResolver expressionResolver;

    public VariableDeclarationOperationReader(final VariableBuilder variableBuilder,
                                              final ExpressionResolver expressionResolver) {
        this.variableBuilder = requireNonNull(variableBuilder);
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of(VAR);
    }

    /*
    Valid:
    var a
    var a = ${expression}
    ---------------------
    Not valid:
    var
    var a =
    var a +
    var + a
    var = a
    var a + 3
    var a 3 =
     */
    @Override
    protected void validate(final SourceLine sourceLine) {
        if (sourceLine.getTokenCount() == 1) {
            throw new JavammLineSyntaxError("Missing a variable name", sourceLine);
        }
        if (sourceLine.getTokenCount() == 3) {
            if ("=".equals(sourceLine.getToken(2))) {
                throw new JavammLineSyntaxError("Missing a variable expression", sourceLine);
            } else {
                throw new JavammLineSyntaxError("Missing or invalid position of '='", sourceLine);
            }
        }
        if (sourceLine.getTokenCount() > 3 && !"=".equals(sourceLine.getToken(2))) {
            throw new JavammLineSyntaxError("Missing or invalid position of '='", sourceLine);
        }
    }

    @Override
    protected VariableDeclarationOperation get(final SourceLine sourceLine,
                                               final ListIterator<SourceLine> iterator) {
        final Variable variable = variableBuilder.build(sourceLine.getToken(1), sourceLine);
        final Expression expression = getVariableExpression(sourceLine);
        return new VariableDeclarationOperation(sourceLine, isConstant(), variable, expression);
    }

    protected boolean isConstant() {
        return false;
    }

    private Expression getVariableExpression(final SourceLine sourceLine) {
        if (sourceLine.getTokenCount() == 2) {
            return NullValueExpression.getInstance();
        } else {
            return expressionResolver.resolve(sourceLine.subList(3), sourceLine);
        }
    }
}
