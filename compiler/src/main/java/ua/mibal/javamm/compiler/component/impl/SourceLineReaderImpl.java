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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ua.mibal.javamm.code.fragment.SourceCode;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.compiler.component.SourceLineReader;
import ua.mibal.javamm.compiler.component.TokenParser;
import ua.mibal.javamm.compiler.model.TokenParserResult;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class SourceLineReaderImpl implements SourceLineReader {

    private final TokenParser tokenParser;

    public SourceLineReaderImpl(final TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    @Override
    public List<SourceLine> read(final SourceCode sourceCode) {
        final List<SourceLine> list = new ArrayList<>();
        final String moduleName = sourceCode.getModuleName();
        int number = 1;
        boolean multilineCommentStarted = false;
        for (final String line : sourceCode.getLines()) {
            final TokenParserResult tokenParserResult = tokenParser.parseLine(line, multilineCommentStarted);
            if (tokenParserResult.isNotEmpty()) {
                list.add(new SourceLine(moduleName, number, tokenParserResult.getTokens()));
            }
            multilineCommentStarted = tokenParserResult.isMultilineCommentStarted();
            number++;
        }
        return Collections.unmodifiableList(list);
    }
}
