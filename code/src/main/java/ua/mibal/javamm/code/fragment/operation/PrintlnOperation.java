/*
 * Copyright (c) 2022. http://t.me/mibal_ua
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

import static java.util.Objects.requireNonNull;
import ua.mibal.javamm.code.fragment.Operation;
import ua.mibal.javamm.code.fragment.SourceLine;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public final class PrintlnOperation extends AbstractOperation implements Operation {

    private final String text;

    public PrintlnOperation(final SourceLine sourceLine, final String text) {
        super(sourceLine);
        this.text = requireNonNull(text);
    }

    public String getText() {
        return text;
    }
}
