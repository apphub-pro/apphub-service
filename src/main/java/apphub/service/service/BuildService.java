/*
 * Copyright (C) 2014 Dmitry Kotlyarov, Dmitriy Rogozhin.
 * All Rights Reserved.
 *
 * CONFIDENTIAL
 *
 * All information contained herein is, and remains the property
 * of copyright holders. The intellectual and technical concepts
 * contained herein are proprietary to copyright holders and may
 * be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission
 * is obtained from copyright holders.
 */

package apphub.service.service;

import apphub.service.api.Build;
import apphub.service.api.IBuildService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.BuildRepository;
import apphub.staff.repository.EnvironmentUserRepository;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class BuildService implements IBuildService {
    protected final Database database;
    protected final BuildRepository buildRepository;
    protected final EnvironmentUserRepository environmentUserRepository;

    public BuildService(Database database, BuildRepository buildRepository, EnvironmentUserRepository environmentUserRepository) {
        this.database = database;
        this.buildRepository = buildRepository;
        this.environmentUserRepository = environmentUserRepository;
    }

    @Override
    public Build get(String secret, String application, String version, String environment) {
        try (Transaction tx = new Transaction(database, secret)) {
            if (environmentUserRepository.exists(tx, environment, tx.getUser())) {
                return buildRepository.get(tx, application, version, environment);
            } else {
                throw new ServerErrorException(String.format("Environment user with environment '%s' and user '%s' is not found", environment, tx.getUser()), Response.Status.NOT_FOUND);
            }
        }
    }

    @Override
    public Build put(String secret, Build build) {
        return null;
    }

    @Override
    public Build post(String secret, Build build) {
        return null;
    }

    @Override
    public Build delete(String secret, String application, String version, String environment) {
        return null;
    }
}
