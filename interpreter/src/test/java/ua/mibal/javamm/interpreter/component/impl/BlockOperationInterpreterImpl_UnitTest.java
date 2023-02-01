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

import java.util.Set;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.mibal.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.exception.ConfigException;
import ua.mibal.javamm.code.fragment.Operation;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.code.fragment.operation.Block;
import ua.mibal.javamm.interpreter.component.BlockOperationInterpreter;
import ua.mibal.javamm.interpreter.component.OperationInterpreter;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class BlockOperationInterpreterImpl_UnitTest {

    @Mock
    private OperationInterpreter operationInterpreter1;

    @Mock
    private OperationInterpreter operationInterpreter2;

    @Test
    @Order(1)
    void Should_throw_ConfigException_if_duplicates_in_operation_interpreters() {
        when(operationInterpreter1.getOperationClass()).thenReturn(TestOperation.class);
        when(operationInterpreter2.getOperationClass()).thenReturn(TestOperation.class);
        when(operationInterpreter1.toString()).thenReturn("operationInterpreter");
        when(operationInterpreter2.toString()).thenReturn("operationInterpreter");

        final ConfigException exception = assertThrows(ConfigException.class,
            () -> new BlockOperationInterpreterImpl(Set.of(
                operationInterpreter1, operationInterpreter2
            )));
        assertEquals(format(
            "Duplicate of OperationInterpreter found: " +
            "operation=%s, interpreter1=%s, interpreter2=%s",
            "ua.mibal.javamm.interpreter.component.impl.BlockOperationInterpreterImpl_UnitTest$TestOperation",
            operationInterpreter1,
            operationInterpreter2
        ), exception.getMessage());
    }

    @Test
    @Order(2)
    void interpret_should_delegate_the_call_to_appropriate_operation_interpreter() {
        final TestOperation testOperation = new TestOperation();
        when(operationInterpreter1.getOperationClass()).thenReturn(TestOperation.class);
        final BlockOperationInterpreter interpreter = new BlockOperationInterpreterImpl(Set.of(operationInterpreter1));
        final Block block = new Block(testOperation, EMPTY_SOURCE_LINE);

        assertDoesNotThrow(() -> interpreter.interpret(block));
        verify(operationInterpreter1).interpret(testOperation);
    }

    @Test
    @Order(3)
    void interpret_should_throw_ConfigException_if_operation_is_not_supported() {
        final BlockOperationInterpreterImpl interpreter = new BlockOperationInterpreterImpl(Set.of());
        final Block block = new Block(new TestOperation(), EMPTY_SOURCE_LINE);

        final ConfigException exception = assertThrows(ConfigException.class,
            () -> interpreter.interpret(block));
        assertEquals("OperationInterpreter not defined for class " +
            "ua.mibal.javamm.interpreter.component.impl.BlockOperationInterpreterImpl_UnitTest$TestOperation",
            exception.getMessage());
    }

    /**
     * @author Michael Balakhon
     * @link t.me/mibal_ua.
     */
    private class TestOperation implements Operation {

        @Override
        public SourceLine getSourceLine() {
            return EMPTY_SOURCE_LINE;
        }
    }
}
