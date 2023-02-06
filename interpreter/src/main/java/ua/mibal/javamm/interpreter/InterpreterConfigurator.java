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

package ua.mibal.javamm.interpreter;

import ua.mibal.javamm.code.component.ExpressionContext;
import ua.mibal.javamm.interpreter.component.BlockOperationInterpreter;
import ua.mibal.javamm.interpreter.component.ExpressionEvaluator;
import ua.mibal.javamm.interpreter.component.ExpressionUpdater;
import ua.mibal.javamm.interpreter.component.OperationInterpreter;
import ua.mibal.javamm.interpreter.component.impl.BlockOperationInterpreterImpl;
import ua.mibal.javamm.interpreter.component.impl.ExpressionContextImpl;
import ua.mibal.javamm.interpreter.component.impl.InterpreterImpl;
import ua.mibal.javamm.interpreter.component.impl.operation.simple.PrintlnOperationInterpreter;

import java.util.Set;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class InterpreterConfigurator {

    private final Set<ExpressionEvaluator<?>> expressionEvaluators = Set.of(
            //TODO Add here
    );

    private final Set<ExpressionUpdater<?>> expressionUpdaters = Set.of(
            //TODO Add here
    );

    private final ExpressionContext expressionContext =
            new ExpressionContextImpl(expressionEvaluators, expressionUpdaters);

    private final Set<OperationInterpreter<?>> operationInterpreters = Set.of(
        new PrintlnOperationInterpreter(expressionContext)
    );

    private final BlockOperationInterpreter blockOperationInterpreter =
        new BlockOperationInterpreterImpl(operationInterpreters);

    private final Interpreter interpreter = new InterpreterImpl(blockOperationInterpreter);

    public Interpreter getInterpreter() {
        return interpreter;
    }
}
