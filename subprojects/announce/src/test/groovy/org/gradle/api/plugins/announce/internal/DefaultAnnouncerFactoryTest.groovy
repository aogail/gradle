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
package org.gradle.api.plugins.announce.internal

import org.gradle.api.Project
import org.gradle.api.plugins.announce.AnnouncePluginExtension
import org.gradle.os.OperatingSystem
import org.gradle.util.HelperUtil
import spock.lang.Specification

/**
 * @author Hans Dockter
 */
class DefaultAnnouncerFactoryTest extends Specification {
    AnnouncePluginExtension announcePluginConvention = new AnnouncePluginExtension(project)
    DefaultAnnouncerFactory announcerFactory = new DefaultAnnouncerFactory(announcePluginConvention)
    Project project = HelperUtil.createRootProject()

    def createForTwitter() {
        announcePluginConvention.username = 'username'
        announcePluginConvention.password = 'password'

        when:
        Twitter twitter = announcerFactory.createAnnouncer('twitter')

        then:
        twitter.username == announcePluginConvention.username
        twitter.password == announcePluginConvention.password
    }

    def createForSnarl() {
        expect:
        announcerFactory.createAnnouncer('snarl') instanceof Snarl
    }

    def createForNotifySend() {
        expect:
        announcerFactory.createAnnouncer('notify-send') instanceof NotifySend
    }

    def createForGrowl() {
        expect:
        announcerFactory.createAnnouncer('growl') instanceof Growl
    }

    def createForLocal() {
        def expectedType
        if (OperatingSystem.current().windows) {
            expectedType = Snarl
        } else if (OperatingSystem.current().macOsX) {
            expectedType = Growl
        } else {
            expectedType = NotifySend
        }

        expect:
        expectedType.isInstance(announcerFactory.createAnnouncer('local'))
    }

    def createWithUnknownType() {
        expect:
        announcerFactory.createAnnouncer('inter-galaxy-announcer') instanceof UnknownAnnouncer
    }
}