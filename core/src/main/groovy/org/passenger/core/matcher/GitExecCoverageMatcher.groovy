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
package org.passenger.core.matcher

import org.passenger.core.executor.AntExecutor
import org.passenger.core.executor.ExecutionResult
import org.passenger.core.executor.ExternalCommandException
import org.passenger.core.executor.ExternalCommandExecutor

class GitExecCoverageMatcher implements CoverageMatcher {
    static final EXECUTABLE = 'git'

    @Override
    CoverageMatch match(File projectDir, List<File> files) {
        CoverageMatch coverageMatch = new CoverageMatch()
        ExternalCommandExecutor executor = new AntExecutor()

        files.each { file ->
            String line = "--no-pager --git-dir=$projectDir/.git --work-tree=$projectDir blame $file"
            ExecutionResult result = executor.exec(EXECUTABLE, line)

            if(result.success) {
                countUsernameMatches(coverageMatch, line)
            }
            else {
                throw new ExternalCommandException(result.error)
            }
        }

        coverageMatch
    }

    private void countUsernameMatches(CoverageMatch coverageMatch, String line) {
        def matcher = line =~ /^([\^a-z0-9]+)( [^\(]+)? \((.*?) \d{4}-\d{2}-\d{2}/

        if(matcher.find()) {
            String username = matcher.group(3)

            if(!coverageMatch.lineCountPerUsername.containsKey(username)) {
                coverageMatch.lineCountPerUsername[username] = 1
            }
            else {
                coverageMatch.lineCountPerUsername[username] = coverageMatch.lineCountPerUsername[username] + 1
            }
        }
    }
}
