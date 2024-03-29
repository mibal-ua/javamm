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

import ua.mibal.javamm.code.Variable;
import ua.mibal.javamm.code.fragment.Expression;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.code.fragment.expression.ConstantExpression;
import ua.mibal.javamm.code.fragment.expression.NullValueExpression;
import ua.mibal.javamm.code.fragment.expression.TypeExpression;
import ua.mibal.javamm.code.fragment.expression.VariableExpression;
import ua.mibal.javamm.code.syntax.Keywords;
import ua.mibal.javamm.compiler.component.SingleTokenExpressionBuilder;
import ua.mibal.javamm.compiler.component.VariableBuilder;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static ua.mibal.javamm.code.syntax.Delimiters.SIGNIFICANT_TOKEN_DELIMITERS;
import static ua.mibal.javamm.code.syntax.Delimiters.STRING_DELIMITERS;
import static ua.mibal.javamm.code.syntax.SyntaxUtils.isValidSyntaxToken;

/**
 * @author Mykhailo Balakhon
 * @link https://t.me/mibal_ua
 */
public class SingleTokenExpressionBuilderImpl implements SingleTokenExpressionBuilder {

    private final VariableBuilder variableBuilder;

    public SingleTokenExpressionBuilderImpl(final VariableBuilder variableBuilder) {
        this.variableBuilder = requireNonNull(variableBuilder);
    }

    @Override
    public boolean canBuild(final List<String> tokens) {
        return tokens.size() == 1 &&
                isValidSyntaxToken(tokens.get(0)) &&
                !SIGNIFICANT_TOKEN_DELIMITERS.contains(tokens.get(0));
    }

    /*
    var a = 'Hello world'
    var a = "Hello world"
    var a = null
    var a = true
    var a = false
    var a = 1
    var a = 1.1
     */
    @Override
    public Expression build(final List<String> expressionTokens, final SourceLine sourceLine) {
        final String value = expressionTokens.get(0);
        Expression expression;
        if (TypeExpression.is(value)) {
            expression = TypeExpression.of(value);
        } else if (STRING_DELIMITERS.contains(value.charAt(0))) {
            expression = buildStringExpression(value, sourceLine);
        } else if (Keywords.NULL.equals(value)) {
            expression = NullValueExpression.getInstance();
        } else if (Keywords.TRUE.equals(value) || Keywords.FALSE.equals(value)) {
            expression = ConstantExpression.valueOf(Boolean.parseBoolean(value));
        } else {
            try {
                expression = ConstantExpression.valueOf(Integer.parseInt(value));
            } catch (final NumberFormatException integerFormatException) {
                try {
                    expression = ConstantExpression.valueOf(Double.parseDouble(value));
                } catch (final NumberFormatException doubleFormatException) {
                    final Variable variable = variableBuilder.build(value, sourceLine);
                    expression = new VariableExpression(variable);
                }
            }
        }
        return expression;
    }

    protected Expression buildStringExpression(final String token, final SourceLine sourceLine) {
        final String stringDelimiter = token.substring(0, 1);
        if (token.length() == 1) {
            throw new JavammLineSyntaxError(
                    format("%s expected at the end of the string token",
                            stringDelimiter), sourceLine);
        }
        if (!token.endsWith(stringDelimiter)) {
            final char last = token.charAt(token.length() - 1);
            if (STRING_DELIMITERS.contains(last)) {
                throw new JavammLineSyntaxError(
                        format("%s expected at the end of the string token instead of %s",
                                stringDelimiter, last), sourceLine);
            }
            throw new JavammLineSyntaxError(
                    format("%s expected at the end of the string token",
                            stringDelimiter), sourceLine);
        }
        return ConstantExpression.valueOf(token.substring(1, token.length() - 1));
    }
}
