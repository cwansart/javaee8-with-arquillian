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
package de.openknowledge.javaee8.application.hello;

import de.openknowledge.javaee8.domain.hello.Greeting;
import de.openknowledge.javaee8.domain.hello.GreetingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

  private static final Logger LOG = LoggerFactory.getLogger(HelloWorldResource.class);

  @Inject
  private GreetingRepository repository;

  @GET
  @Path("all")
  @Operation(description = "Returns a list of all known greetings", responses = {
      @ApiResponse(responseCode = "200", description = "OK")
  })
  public Response getAllGreetings() {
    LOG.info("Calling getAllGreetings");

    List<Greeting> greetings = repository.getAllGreetings();

    return Response.status(Response.Status.OK)
        .entity(new GenericEntity<List<Greeting>>(greetings){})
        .build();
  }

  @GET
  @Path("{name}")
  @Operation(description = "Say hello to someone", responses = {
      @ApiResponse(responseCode = "200", description = "OK")
  })
  public Response sayHello(@Parameter(description = "name") @PathParam("name") final String name) {
    LOG.info("Calling sayHello with name={}", name);

    String hello = String.format("Hello %s!", name);

    return Response.status(Response.Status.OK)
        .entity(new MessageDTO(hello))
        .build();
  }

  @GET
  @Operation(description = "Say hello world", responses = {
      @ApiResponse(responseCode = "200", description = "OK")
  })
  public Response sayHelloWorld() {
    LOG.info("Calling sayHelloWorld");

    return sayHello("World");
  }
}
