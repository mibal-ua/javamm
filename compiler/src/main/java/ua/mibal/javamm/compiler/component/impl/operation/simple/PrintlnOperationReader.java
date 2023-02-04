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

import ua.mibal.javamm.code.fragment.Expression;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.code.fragment.operation.PrintlnOperation;
import ua.mibal.javamm.compiler.component.ExpressionResolver;
import ua.mibal.javamm.compiler.component.OperationReader;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import ua.mibal.javamm.compiler.component.impl.operation.AbstractOperationReader;

import java.util.ListIterator;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class PrintlnOperationReader extends AbstractOperationReader<PrintlnOperation> implements OperationReader {

    private final ExpressionResolver expressionResolver;

    public PrintlnOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of("println");
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        if (!")".equals(sourceLine.getLast())) {
            throw new JavammLineSyntaxError("Expected ')' at the end of line", sourceLine);
        }
        if (!"(".equals(sourceLine.getToken(1))) {
            throw new JavammLineSyntaxError("Expected '(' after 'println'", sourceLine);
        }
    }

    @Override
    protected PrintlnOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        final Expression expression = expressionResolver.resolve(// println ( 'text' )
                sourceLine.subList(2, sourceLine.getTokenCount() - 1), sourceLine);
        return new PrintlnOperation(sourceLine, expression);
    }
}
