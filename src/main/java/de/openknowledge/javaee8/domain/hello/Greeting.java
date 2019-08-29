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

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
public class Greeting extends AbstractEntity<Integer> {

    @Id
    private Integer id;

    private String greeting;

    protected Greeting() {
        super();
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getGreeting() {
        return greeting;
    }

    public static GreetingBuilder newBuilder() {
        return new GreetingBuilder();
    }

    public static class GreetingBuilder {

        private Greeting greeting = new Greeting();

        public GreetingBuilder withId(final Integer id) {
            this.greeting.id = notNull(id, "id must not be null");
            return this;
        }

        public GreetingBuilder withGreeting(final String greeting) {
            this.greeting.greeting = notNull(greeting, "greeting must not be null");
            return this;
        }

        public Greeting build() {
            return greeting;
        }
    }
}
