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

import apphub.service.api.EnvironmentUser;
import apphub.service.api.IEnvironmentUserService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentUserRepository;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class EnvironmentUserService implements IEnvironmentUserService {
    protected final Database database;
    protected final EnvironmentUserRepository environmentUserRepository;

    public EnvironmentUserService(Database database, EnvironmentUserRepository environmentUserRepository) {
        this.database = database;
        this.environmentUserRepository = environmentUserRepository;
    }

    @Override
    public EnvironmentUser get(String secret, String environment, String user) {
        try (Transaction tx = new Transaction(database, secret)) {
            if (environmentUserRepository.exists(tx, environment, tx.getUser())) {
                return environmentUserRepository.get(tx, environment, user);
            } else {
                throw new ServerErrorException(String.format("Environment user with environment '%s' and user '%s' is not found", environment, tx.getUser()), Response.Status.NOT_FOUND);
            }
        }
    }

    @Override
    public List<EnvironmentUser> list(String secret, String environment) {
        return null;
    }

    @Override
    public EnvironmentUser put(String secret, EnvironmentUser environmentUser) {
        return null;
    }

    @Override
    public EnvironmentUser post(String secret, EnvironmentUser environmentUser) {
        return null;
    }

    @Override
    public EnvironmentUser delete(String secret, String environment, String user) {
        return null;
    }
}
