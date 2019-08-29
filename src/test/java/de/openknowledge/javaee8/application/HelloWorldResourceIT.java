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
package de.openknowledge.javaee8.application;

import de.openknowledge.javaee8.application.hello.HelloWorldResource;
import de.openknowledge.javaee8.application.hello.MessageDTO;
import de.openknowledge.javaee8.domain.hello.Greeting;
import de.openknowledge.javaee8.domain.hello.GreetingRepository;
import de.openknowledge.javaee8.infrastructure.domain.entity.AbstractEntity;
import de.openknowledge.javaee8.infrastructure.domain.entity.Identifiable;
import de.openknowledge.javaee8.infrastructure.infrastructure.persistence.EntityManagerProducer;
import de.openknowledge.javaee8.infrastructure.stereotypes.Repository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
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

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URL;

import static org.hamcrest.Matchers.is;

@RunWith(Arquillian.class)
public class HelloWorldResourceIT {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldResourceIT.class);

    @Deployment
    public static Archive<?> createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
            .addAsLibraries(pomFile.resolve("io.rest-assured:rest-assured").withTransitivity().asFile())
            .addClasses(JaxRsActivator.class, HelloWorldResource.class, MessageDTO.class, Greeting.class)
            .addClasses(GreetingRepository.class, EntityManagerProducer.class, AbstractEntity.class, Identifiable.class)
            .addClasses(Repository.class)
            .addAsResource("import.sql")
            .addAsResource("META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        LOG.info("Generated: {}", webArchive.toString(true));

        return webArchive;
    }

    @ArquillianResource
    private URL url;

    @Test
    public void shouldReturnFiveGreetings() throws Exception {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .when()
            .get(getAllGreetingsUri())
            .then()
            .statusCode(200)
            .body("size()", is(5));
    }

    @Test
    public void shouldReturnHelloWorld() throws Exception {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .when()
            .get(getHelloUri())
            .then()
            .statusCode(200)
            .body("message", is("Hello World!"));
    }

    @Test
    public void shouldReturnHelloMars() throws Exception {
        RestAssured.given().
            contentType(ContentType.JSON)
            .when()
            .get(getHelloNameUri("Mars"))
            .then()
            .statusCode(200)
            .body("message", is("Hello Mars!"));
    }

    private URI getAllGreetingsUri() throws Exception {
        return UriBuilder.fromUri(url.toURI()).path("api").path("hello").path("all").build();
    }

    private URI getHelloUri() throws Exception {
        return UriBuilder.fromUri(url.toURI()).path("api").path("hello").build();
    }

    private URI getHelloNameUri(final String name) throws Exception {
        return UriBuilder.fromUri(getHelloUri()).path(name).build();
    }
}
