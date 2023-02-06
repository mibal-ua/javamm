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

package ua.mibal.javamm.interpreter.component.impl.operation.simple;

import ua.mibal.javamm.code.component.ExpressionContext;
import ua.mibal.javamm.code.fragment.operation.PrintlnOperation;
import ua.mibal.javamm.interpreter.component.impl.operation.AbstractOperationInterpreter;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public final class PrintlnOperationInterpreter extends AbstractOperationInterpreter<PrintlnOperation> {

    public PrintlnOperationInterpreter(final ExpressionContext expressionContext) {
        super(expressionContext);
    }

    @Override
    public Class<PrintlnOperation> getOperationClass() {
        return PrintlnOperation.class;
    }

    @Override
    protected void interpretOperation(final PrintlnOperation operation) {
        System.out.println(operation.getExpression().getValue(expressionContext));
    }
}
