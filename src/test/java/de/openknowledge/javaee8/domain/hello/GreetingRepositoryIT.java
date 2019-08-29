/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.javaee8.domain.hello;

import de.openknowledge.javaee8.infrastructure.domain.entity.AbstractEntity;
import de.openknowledge.javaee8.infrastructure.domain.entity.Identifiable;
import de.openknowledge.javaee8.infrastructure.infrastructure.persistence.EntityManagerProducer;
import de.openknowledge.javaee8.infrastructure.stereotypes.Repository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
public class GreetingRepositoryIT {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingRepositoryIT.class);

    @Inject
    private GreetingRepository repository;

    @Deployment
    public static Archive<?> createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
            .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
            .addAsLibraries(pomFile.resolve("org.apache.commons:commons-lang3").withTransitivity().asFile())
            .addClasses(GreetingRepository.class, Repository.class, EntityManagerProducer.class, Greeting.class)
            .addClasses(AbstractEntity.class, Identifiable.class)
            .addAsResource("import.sql")
            .addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        LOG.info("Generated: {}", webArchive.toString(true));

        return webArchive;
    }

    @Test
    public void shouldReturnFiveGreetings() {
        List<Greeting> allGreetings = repository.getAllGreetings();

        assertThat(allGreetings).hasSize(5);
        assertThat(allGreetings).containsAll(Arrays.asList(
            Greeting.newBuilder().withId(1).withGreeting("Hallo").build(),
            Greeting.newBuilder().withId(1).withGreeting("Hei").build(),
            Greeting.newBuilder().withId(1).withGreeting("Bonjour").build(),
            Greeting.newBuilder().withId(1).withGreeting("Hola").build(),
            Greeting.newBuilder().withId(1).withGreeting("Hej").build()
        ));
    }
}
