/*
 * Copyright 2016 Schibsted ASA.
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
 */

package com.netflix.spinnaker.igor.build.artifact.identifier

import spock.lang.Specification
import spock.lang.Unroll

class FileArtifactIdentifierSpec extends Specification {

    @Unroll
    def "identify package type from file name"(){
        given:
        FileArtifactIdentifier fileArtifactIdentifier = new FileArtifactIdentifier()

        expect:
        fileArtifactIdentifier.artifactType(fileName) == expectedType

        where:
        fileName                                    || expectedType
        "api_1.1.1-h01.sha123_all.deb"              || "deb"
        "openmotif22-libs-2.2.4-192.1.3.x86_64.rpm" || "rpm"
        "my-jar.jar"                                || "jar"
    }

}
