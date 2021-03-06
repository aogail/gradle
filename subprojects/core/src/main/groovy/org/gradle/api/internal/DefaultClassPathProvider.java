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
package org.gradle.api.internal;

import org.gradle.api.internal.classpath.Module;
import org.gradle.api.internal.classpath.ModuleRegistry;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

public class DefaultClassPathProvider implements ClassPathProvider {
    private final ModuleRegistry moduleRegistry;

    public DefaultClassPathProvider(ModuleRegistry moduleRegistry) {
        this.moduleRegistry = moduleRegistry;
    }

    public Set<File> findClassPath(String name) {
        if (name.equals("GRADLE_RUNTIME")) {
            Set<File> classpath = new LinkedHashSet<File>();
            for (Module module : moduleRegistry.getModule("gradle-launcher").getAllRequiredModules()) {
                classpath.addAll(module.getClasspath());
            }
            return classpath;
        }
        if (name.equals("GRADLE_CORE")) {
            return moduleRegistry.getModule("gradle-core").getImplementationClasspath();
        }
        if (name.equals("COMMONS_CLI")) {
            return moduleRegistry.getExternalModule("commons-cli").getClasspath();
        }
        if (name.equals("ANT")) {
            Set<File> classpath = new LinkedHashSet<File>();
            classpath.addAll(moduleRegistry.getExternalModule("ant").getClasspath());
            classpath.addAll(moduleRegistry.getExternalModule("ant-launcher").getClasspath());
            return classpath;
        }
        if (name.equals("GROOVY")) {
            return moduleRegistry.getExternalModule("groovy-all").getClasspath();
        }

        return null;
    }
}
