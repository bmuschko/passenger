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
package org.passenger.core.collector

class RecursiveDirectoryFileCollector implements FileCollector {
    static final String DEFAULT_JAVA_FILE_PATTERN = '**/*.java'
    static final String DEFAULT_GROOVY_FILE_PATTERN = '**/*.groovy'

    @Override
    List<File> collect(File source) {
        if(!source.exists()) {
            throw new SourceNotExistentException("Source $source.absolutePath does not exist")
        }

        def actualFiles = []
        List<String> files = new FileNameFinder().getFileNames(source.absolutePath, getDefaultIncludes())

        files.each { file ->
            actualFiles << new File(file)
        }

        actualFiles
    }

    /**
     * Gets default includes.
     *
     * @return Includes
     */
    private String getDefaultIncludes() {
        "$DEFAULT_JAVA_FILE_PATTERN,$DEFAULT_GROOVY_FILE_PATTERN".toString()
    }
}
