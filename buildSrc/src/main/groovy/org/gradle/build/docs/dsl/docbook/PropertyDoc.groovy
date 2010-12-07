/*
 * Copyright 2010 the original author or authors.
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
package org.gradle.build.docs.dsl.docbook

import org.gradle.build.docs.dsl.model.PropertyMetaData
import org.w3c.dom.Element

class PropertyDoc {
    private final String id
    private final String name
    final List<Element> comment
    private final List<Element> additionalValues
    private final PropertyMetaData metaData

    PropertyDoc(PropertyMetaData propertyMetaData, List<Element> comment, List<Element> additionalValues) {
        name = propertyMetaData.name
        this.metaData = propertyMetaData
        id = "${propertyMetaData.ownerClass.className}:$name"
        this.comment = comment
        this.additionalValues = additionalValues
    }

    String getId() {
        return id
    }

    String getName() {
        return name
    }

    PropertyMetaData getMetaData() {
        return metaData
    }

    Element getDescription() {
        return comment.find { it.nodeName == 'para' }
    }

    List<Element> getAdditionalValues() {
        return additionalValues
    }
}

