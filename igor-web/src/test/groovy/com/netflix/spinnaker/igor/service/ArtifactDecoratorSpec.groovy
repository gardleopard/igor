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

package com.netflix.spinnaker.igor.service

import com.netflix.spinnaker.igor.build.model.GenericArtifact
import spock.lang.Specification
import spock.lang.Unroll


class ArtifactDecoratorSpec extends Specification {

    @Unroll
    def "Decorate"() {
        given:
        ArtifactDecorator artifactDecorator = new ArtifactDecorator()

        when:
        GenericArtifact genericArtifact = new GenericArtifact(reference, reference, reference)
        artifactDecorator.decorate(genericArtifact)

        then:
        genericArtifact.name    == name
        genericArtifact.version == version
        genericArtifact.type    == type

        where:
        reference || name || version || type
        "openmotif22-libs-2.2.4-192.1.3.x86_64.rpm" || "openmotif22-libs" || "2.2.4" || "rpm"
        "api_1.1.1-h01.sha123_all.deb" || "api"  || "1.1.1-h01.sha123" || "deb"
        "unknown-3.2.1.apk" || null || null || null
    }
}
