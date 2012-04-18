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
package org.passenger.core.report

import groovy.xml.MarkupBuilder

class XmlReportGenerator implements ReportGenerator {
    @Override
    void generate(File destFile, Map<String, Integer> statistics) {
        def writer = new FileWriter(destFile)
        def builder = new MarkupBuilder(writer)
        builder.mkp.xmlDeclaration(version: '1.0', encoding: 'UTF-8')

        builder.'passenger-results'(generated: new Date().time) {
            builder.collaborators {
                statistics.each { key, value ->
                    builder.collaborator(name: key, percentage: value)
                }
            }
        }
    }
}
