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

import com.netflix.spinnaker.igor.build.artifact.decorator.ArtifactDetailsDecorator
import com.netflix.spinnaker.igor.build.artifact.decorator.DebDetailsDecorator
import com.netflix.spinnaker.igor.build.artifact.decorator.RpmDetailsDecorator
import com.netflix.spinnaker.igor.build.artifact.identifier.ArtifactTypeIdentifier
import com.netflix.spinnaker.igor.build.artifact.identifier.FileArtifactIdentifier
import com.netflix.spinnaker.igor.build.model.GenericArtifact
import com.netflix.spinnaker.igor.build.model.GenericBuild

class ArtifactDecorator {

    Map<String, ArtifactDetailsDecorator> artifactDetailsDecorators = new HashMap<String, ArtifactDetailsDecorator>()
    List<ArtifactTypeIdentifier> artifactTypeIdentifiers = new ArrayList<ArtifactTypeIdentifier>()

    ArtifactDecorator() {
        artifactDetailsDecorators.put("rpm", new RpmDetailsDecorator())
        artifactDetailsDecorators.put("deb", new DebDetailsDecorator())
        artifactTypeIdentifiers.add(new FileArtifactIdentifier())
    }

    void decorate(GenericArtifact genericArtifact) {
        String type = getType(genericArtifact.fileName)
        ArtifactDetailsDecorator artifactDetailsDecorator = artifactDetailsDecorators.get(type)
        if(artifactDetailsDecorator) {
            artifactDetailsDecorator.decorate(genericArtifact)

        }
    }

    void decorate(GenericBuild genericBuild) {
        genericBuild.artifacts?.each {
            decorate(it)
        }
    }

    String getType( String reference ) {
        for(ArtifactTypeIdentifier identifier: artifactTypeIdentifiers) {
            String artifactType = identifier.artifactType(reference)
            if(artifactType) {
                return artifactType
            }
        }
        return ""
    }
}
