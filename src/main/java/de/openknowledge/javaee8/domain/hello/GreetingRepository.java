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

import de.openknowledge.javaee8.infrastructure.stereotypes.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GreetingRepository {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingRepository.class);

    @Inject
    private EntityManager entityManager;

    public List<Greeting> getAllGreetings() {
        LOG.info("Get all greeting");

        TypedQuery<Greeting> query = entityManager.createQuery("select g from Greeting g", Greeting.class);
        List<Greeting> resultList = query.getResultList();

        LOG.info("Found {} greetings", resultList.size());

        return resultList;
    }
}
