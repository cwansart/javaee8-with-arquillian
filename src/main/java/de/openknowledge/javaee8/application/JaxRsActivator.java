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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
@OpenAPIDefinition(info =
@Info(title = "Base API", description = "Provides access to the API operations", version = "1.0-SNAPSHOT"),
    servers = @Server(url = "http://{host}:{port}/{context-root}", variables = {
        @ServerVariable(name = "host", defaultValue = "localhost"),
        @ServerVariable(name = "port", defaultValue = "8080"),
        @ServerVariable(name = "context-root", defaultValue = "javaee8")
    }))
public class JaxRsActivator extends Application {
}
