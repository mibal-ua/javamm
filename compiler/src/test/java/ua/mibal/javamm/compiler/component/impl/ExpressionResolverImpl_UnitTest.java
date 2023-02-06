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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.compiler.component.ExpressionBuilder;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ExpressionResolverImpl_UnitTest {

    @Mock
    private ExpressionBuilder expressionBuilder;

    private ExpressionResolverImpl expressionResolver;

    private final SourceLine sourceLine = new SourceLine("test", 1, List.of("var", "a"));

    private List<String> expressionTokens;

    @BeforeEach
    void beforeEach() {
        expressionResolver = new ExpressionResolverImpl(Set.of(expressionBuilder));
    }

    @Test
    @Order(1)
    void resolve_should_invoke_expressionBuilder_build() {
        when(expressionBuilder.canBuild(expressionTokens)).thenReturn(true);

        expressionResolver.resolve(expressionTokens, sourceLine);
        verify(expressionBuilder, times(1)).build(expressionTokens, sourceLine);
    }

    @Test
    @Order(2)
    void resolve_should_throw_JavammLineSyntaxError_if_() {
        expressionTokens = List.of("var", "a");

        JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class,
                () -> expressionResolver.resolve(expressionTokens, sourceLine));
        assertEquals(
                "Syntax error in 'test' [Line: 1]: Unsupported expression: 'var a'",
                error.getMessage());
        verify(expressionBuilder, never()).build(expressionTokens, sourceLine);
    }

}
