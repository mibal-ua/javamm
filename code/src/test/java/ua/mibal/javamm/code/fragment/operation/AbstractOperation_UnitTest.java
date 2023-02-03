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

package ua.mibal.javamm.code.fragment.operation;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import ua.mibal.javamm.code.fragment.SourceLine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AbstractOperation_UnitTest {

    @Test
    void Should_use_SourceLine_toString() {
        final SourceLine sourceLine = new SourceLine("test", 1, List.of("var", "b", "=", "2"));
        assertEquals(sourceLine.toString(), new AbstractOperation(sourceLine) {}.toString());
    }
}
