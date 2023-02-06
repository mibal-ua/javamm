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

package ua.mibal.javamm.interpreter.component.impl.operation;

import ua.mibal.javamm.code.component.ExpressionContext;
import ua.mibal.javamm.code.fragment.Operation;
import ua.mibal.javamm.interpreter.TerminateInterpreterException;
import ua.mibal.javamm.interpreter.component.OperationInterpreter;

import static java.util.Objects.requireNonNull;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public abstract class AbstractOperationInterpreter<T extends Operation> implements OperationInterpreter<T> {

    protected final ExpressionContext expressionContext;

    public AbstractOperationInterpreter(final ExpressionContext expressionContext) {
        this.expressionContext = requireNonNull(expressionContext);
    }

    @Override
    public final void interpret(final T operation) {
        checkForTerminate();
        interpretOperation(operation);
    }

    protected final void checkForTerminate() {
        if (Thread.interrupted()) {
            throw new TerminateInterpreterException();
        }
    }

    protected abstract void interpretOperation(T operation);
}
