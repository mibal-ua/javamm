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

package ua.mibal.javamm.compiler.component.impl.util;

import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import static java.lang.String.format;
import static ua.mibal.javamm.code.syntax.Keywords.KEYWORDS;

/**
 * @author Mykhailo Balakhon
 * @link t.me/mibal_ua
 */
public final class SyntaxValidationUtils {

    private SyntaxValidationUtils() {
    }

    public static void validateThatFirstCharacterIsLetter(final DeveloperObject developerObject,
                                                          final String name,
                                                          final SourceLine sourceLine) {
        if (!Character.isLetter(name.charAt(0))) {
            throw new JavammLineSyntaxError(format("The %s name must start with letter: '%s'",
                    developerObject.name().toLowerCase(), name), sourceLine);
        }
    }

    public static void validateThatNameIsNotKeyword(final DeveloperObject developerObject,
                                                    final String name,
                                                    final SourceLine sourceLine) {
        if (KEYWORDS.contains(name)) {
            throw new JavammLineSyntaxError(format("The keyword '%s' can't be used as a %s name", name,
                    developerObject.name().toLowerCase()), sourceLine);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public enum DeveloperObject {

        VARIABLE,

        FUNCTION;
    }
}
