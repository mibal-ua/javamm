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

package ua.mibal.javamm.compiler;

import ua.mibal.javamm.compiler.component.BlockOperationReader;
import ua.mibal.javamm.compiler.component.ExpressionBuilder;
import ua.mibal.javamm.compiler.component.ExpressionResolver;
import ua.mibal.javamm.compiler.component.OperationReader;
import ua.mibal.javamm.compiler.component.SingleTokenExpressionBuilder;
import ua.mibal.javamm.compiler.component.SourceLineReader;
import ua.mibal.javamm.compiler.component.TokenParser;
import ua.mibal.javamm.compiler.component.impl.BlockOperationReaderImpl;
import ua.mibal.javamm.compiler.component.impl.CompilerImpl;
import ua.mibal.javamm.compiler.component.impl.ExpressionResolverImpl;
import ua.mibal.javamm.compiler.component.impl.SingleTokenExpressionBuilderImpl;
import ua.mibal.javamm.compiler.component.impl.SourceLineReaderImpl;
import ua.mibal.javamm.compiler.component.impl.TokenParserImpl;
import ua.mibal.javamm.compiler.component.impl.operation.simple.PrintlnOperationReader;

import java.util.Set;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class CompilerConfigurator {

    private final TokenParser tokenParser = new TokenParserImpl();

    private final SourceLineReader sourceLineReader = new SourceLineReaderImpl(tokenParser);

    private final SingleTokenExpressionBuilder singleTokenExpressionBuilder = new SingleTokenExpressionBuilderImpl();

    private final Set<ExpressionBuilder> expressionBuilders = Set.of(
            singleTokenExpressionBuilder
    );

    private final ExpressionResolver expressionResolver = new ExpressionResolverImpl(expressionBuilders);

    private final Set<OperationReader> operationReaders = Set.of(
            new PrintlnOperationReader(expressionResolver)
    );

    private final BlockOperationReader blockOperationReader = new BlockOperationReaderImpl(operationReaders);

    private final Compiler compiler = new CompilerImpl(sourceLineReader, blockOperationReader);

    public Compiler getCompiler() {
        return compiler;
    }
}
