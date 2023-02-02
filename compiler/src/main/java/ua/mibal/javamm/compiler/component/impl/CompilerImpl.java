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

import ua.mibal.javamm.code.fragment.ByteCode;
import ua.mibal.javamm.code.fragment.SourceCode;
import ua.mibal.javamm.code.fragment.SourceLine;
import ua.mibal.javamm.code.fragment.operation.Block;
import ua.mibal.javamm.compiler.Compiler;
import ua.mibal.javamm.compiler.JavammSyntaxError;
import ua.mibal.javamm.compiler.component.BlockOperationReader;
import ua.mibal.javamm.compiler.component.SourceLineReader;
import java.util.List;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class CompilerImpl implements Compiler {

    private final SourceLineReader sourceLineReader;

    private final BlockOperationReader blockOperationReader;

    public CompilerImpl(final SourceLineReader sourceLineReader,
                        final BlockOperationReader blockOperationReader) {
        this.sourceLineReader = sourceLineReader;
        this.blockOperationReader = blockOperationReader;
    }

    @Override
    public ByteCode compile(final SourceCode... sourceCodes) throws JavammSyntaxError {
        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCodes[0]);
        final Block block = blockOperationReader.read(SourceLine.EMPTY_SOURCE_LINE, sourceLines.listIterator());
        return () -> block;
    }
}
