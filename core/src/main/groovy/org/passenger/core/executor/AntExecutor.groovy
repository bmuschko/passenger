/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.passenger.core.executor

class AntExecutor implements ExternalCommandExecutor {
    static final OUTPUT_PROPERTY = 'cmdOut'
    static final ERROR_PROPERTY = 'cmdErr'
    static final RESULT_PROPERTY = 'cmdExit'

    @Override
    ExecutionResult exec(String executable, String commandLine) {
        def ant = new AntBuilder()

        ant.exec(outputproperty: OUTPUT_PROPERTY, errorproperty: ERROR_PROPERTY, resultproperty: RESULT_PROPERTY, failonerror: 'true', executable: executable) {
            arg(line: commandLine)
        }

        new ExecutionResult(resultCode: ant.project.properties.cmdExit?.toInteger(), output: ant.project.properties.cmdOut, error: ant.project.properties.cmdErr)
    }
}
