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

import static java.lang.Thread.currentThread;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.fragment.Operation;
import ua.mibal.javamm.interpreter.TerminateInterpreterException;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class AbstractOperationInterpreter_UnitTest {

    @Mock
    private Operation operation;

    private AbstractOperationInterpreter operationInterpreter;

    @BeforeEach
    void beforeEach(){
        operationInterpreter = spy(new AbstractOperationInterpreter() {
            @Override
            protected void interpretOperation(final Operation operation) {

            }

            @Override
            public Class getOperationClass() {
                return null;
            }
        });
    }

    @Test
    @Order(1)
    void Should_invoke_interpretOperation_after_checkForTerminate() {
        assertDoesNotThrow(() -> operationInterpreter.interpret(operation));
        verify(operationInterpreter).interpretOperation(operation);
    }

    @Test
    @Order(2)
    void Should_throw_TerminateInterpreterException_if_current_thread_is_interrupted() {
        currentThread().interrupt();
        assertThrows(TerminateInterpreterException.class, () -> operationInterpreter.interpret(operation));
        assertFalse(currentThread().isInterrupted());
        verify(operationInterpreter, never()).interpretOperation(operation);
    }
}
