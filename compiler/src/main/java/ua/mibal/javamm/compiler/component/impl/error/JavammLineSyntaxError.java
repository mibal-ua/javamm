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

package ua.mibal.javamm.compiler.component.impl.error;

import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.compiler.JavammSyntaxError;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;


/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class JavammLineSyntaxError extends JavammSyntaxError {

    public JavammLineSyntaxError(final String message,
                                 final SourceLine sourceLine) {
        super(format("Syntax error in '%s' [Line: %s]: %s",
            sourceLine.getModuleName(), sourceLine.getNumber(), requireNonNull(message)));
    }
}
