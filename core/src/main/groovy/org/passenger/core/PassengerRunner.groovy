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
package org.passenger.core

import org.passenger.core.collector.FileCollector
import org.passenger.core.collector.FileCollectorFactory
import org.passenger.core.matcher.CoverageMatch
import org.passenger.core.matcher.CoverageMatcher
import org.passenger.core.matcher.GitExecCoverageMatcher
import org.passenger.core.statistic.PercentageUserStatCalculator
import org.passenger.core.statistic.UserStatCalculator

class PassengerRunner {
    void run(RunnerOptions runnerOptions) {
        FileCollector fileCollector = FileCollectorFactory.instance.getFileCollector(runnerOptions.fileCollectorType)
        List<File> files = fileCollector.collect(runnerOptions.source)

        CoverageMatcher coverageMatcher = new GitExecCoverageMatcher()
        CoverageMatch coverageMatch = coverageMatcher.match(runnerOptions.projectDir, files)

        UserStatCalculator userStatCalculator = new PercentageUserStatCalculator()
        Map<String, Integer> userPercentages = userStatCalculator.calculate(coverageMatch.lineCountPerUsername)
        println "lineCountPerUsername: " + coverageMatch.lineCountPerUsername
        println "Result: " + userPercentages
    }
}
