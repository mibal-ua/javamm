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
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.compiler.component.VariableBuilder;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import static java.util.Objects.requireNonNull;
import static ua.mibal.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import static ua.mibal.javamm.compiler.component.impl.util.SyntaxValidationUtils.DeveloperObject.VARIABLE;
import static ua.mibal.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatFirstCharacterIsLetter;
import static ua.mibal.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatNameIsNotKeyword;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public class VariableBuilderImpl implements VariableBuilder {

    @Override
    public boolean isValid(final String name) {
        try {
            validateVariableName(name, EMPTY_SOURCE_LINE);
            return true;
        } catch (final JavammLineSyntaxError e) {
            return false;
        }
    }

    @Override
    public Variable build(final String name, final SourceLine sourceLine) {
        validateVariableName(name, sourceLine);
        return new VariableImpl(name);
    }

    private void validateVariableName(final String variableName, final SourceLine sourceLine) {
        validateThatFirstCharacterIsLetter(VARIABLE, variableName, sourceLine);
        validateThatNameIsNotKeyword(VARIABLE, variableName, sourceLine);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class VariableImpl implements Variable {

        private final String name;

        private VariableImpl(final String name) {
            this.name = requireNonNull(name);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(final Object o) {
            return name.equals(((Variable) o).getName());
        }

        @Override
        public int hashCode() {
            return getName().hashCode();
        }

        @Override
        public int compareTo(final Variable o) {
            return name.compareTo(o.getName());
        }
    }
}
