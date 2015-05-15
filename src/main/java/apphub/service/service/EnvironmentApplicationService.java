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

import apphub.service.api.EnvironmentApplication;
import apphub.service.api.IEnvironmentApplicationService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentApplicationRepository;
import apphub.staff.repository.EnvironmentRepository;
import apphub.staff.repository.EnvironmentUserRepository;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class EnvironmentApplicationService implements IEnvironmentApplicationService {
    protected final Database database;
    protected final EnvironmentRepository environmentRepository;
    protected final EnvironmentUserRepository environmentUserRepository;
    protected final EnvironmentApplicationRepository environmentApplicationRepository;

    public EnvironmentApplicationService(Database database,
                                         EnvironmentRepository environmentRepository,
                                         EnvironmentUserRepository environmentUserRepository,
                                         EnvironmentApplicationRepository environmentApplicationRepository) {
        this.database = database;
        this.environmentRepository = environmentRepository;
        this.environmentUserRepository = environmentUserRepository;
        this.environmentApplicationRepository = environmentApplicationRepository;
    }

    @Override
    public EnvironmentApplication get(String secret, String environment, String application) {
        try (Transaction tx = new Transaction(database, secret)) {
            if (environmentUserRepository.exists(tx, environment, tx.getUser())) {
                return environmentApplicationRepository.get(tx, environment, application);
            } else {
                throw new ServerErrorException(String.format("Environment user with environment '%s' and user '%s' is not found", environment, tx.getUser()), Response.Status.NOT_FOUND);
            }
        }
    }

    @Override
    public List<EnvironmentApplication> list(String secret, String environment) {
        return null;
    }

    @Override
    public EnvironmentApplication put(String secret, EnvironmentApplication environmentApplication) {
        return null;
    }

    @Override
    public EnvironmentApplication post(String secret, EnvironmentApplication environmentApplication) {
        return null;
    }

    @Override
    public EnvironmentApplication delete(String secret, String environment, String application) {
        return null;
    }
}
