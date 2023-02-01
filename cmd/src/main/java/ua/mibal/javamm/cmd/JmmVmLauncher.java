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

import ua.mibal.javamm.compiler.JavammSyntaxError;
import ua.mibal.javamm.interpreter.JavammRuntimeError;
import ua.mibal.javamm.vm.VirtualMachine;
import ua.mibal.javamm.vm.VirtualMachineBuilder;
import java.io.IOException;

/**
 * @author Michael Balakhon
 * @link t.me/mibal_ua.
 */
public final class JmmVmLauncher {

    private JmmVmLauncher() {
    }

    public static void main(final String[] args) throws IOException {
        final VirtualMachine virtualMachine = new VirtualMachineBuilder().build();
        try {
            virtualMachine.run(new FileSourceCode("cmd/src/main/resources/test.javamm"));
        } catch (final JavammSyntaxError | JavammRuntimeError e) {
            System.err.println(e.getMessage());
        }
    }
}

