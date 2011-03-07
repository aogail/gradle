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
package org.gradle.plugins.idea.configurer

import org.gradle.api.Project

/**
 * @author Szczepan Faber, @date: 06.03.11
 */
class DefaultIdeaAssetsConfigurer implements IdeaAssetsConfigurer {

    def ModuleNameDeduper deduper = new ModuleNameDeduper()

    void configure(Collection<Project> ideaProjects) {
        //better way of sorting?
        def sorted = ideaProjects.sort { it.path.count(":") }
        def ideaModules = sorted.collect { p -> p.ideaModule }
        deduper.dedupeModuleNames(ideaModules)
    }
}