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

package ua.mibal.javamm.cmd;

import java.util.List;
import ua.mibal.javamm.code.fragment.ByteCode;
import ua.mibal.javamm.code.fragment.SourceCode;
import ua.mibal.javamm.compiler.Compiler;
import ua.mibal.javamm.compiler.CompilerConfigurator;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public class JmmVmLauncher {

    public static void main(final String[] args) {
        final Compiler compiler = new CompilerConfigurator().getCompiler();
        final ByteCode byteCode = compiler.compile(new SourceCode() {
            @Override
            public String getModuleName() {
                return "test";
            }

            @Override
            public List<String> getLines() {
                return List.of(
                    "println ( 'HelloWorld' )",
                    "",
                    "println ( 'HelloJava' )"
                );
            }
        });
        System.out.println(byteCode.getCode());
    }
}
