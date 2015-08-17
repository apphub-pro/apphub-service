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

package apphub.service.service.v1;

import apphub.service.v1.api.EnvironmentApplication;
import apphub.service.v1.api.IEnvironmentApplicationService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentApplicationRepository;
import apphub.staff.repository.EnvironmentUserRepository;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class EnvironmentApplicationService implements IEnvironmentApplicationService {
    protected final Database database;
    protected final EnvironmentUserRepository environmentUserRepository;
    protected final EnvironmentApplicationRepository environmentApplicationRepository;

    public EnvironmentApplicationService(Database database,
                                         EnvironmentUserRepository environmentUserRepository,
                                         EnvironmentApplicationRepository environmentApplicationRepository) {
        this.database = database;
        this.environmentUserRepository = environmentUserRepository;
        this.environmentApplicationRepository = environmentApplicationRepository;
    }

    @Override
    public EnvironmentApplication get(String token, String environment, String application) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentApplicationRepository.get(tx, environment, application);
        }
    }

    @Override
    public List<EnvironmentApplication> list(String token, String environment) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentApplicationRepository.findByEnvironment(tx, environment);
        }
    }

    @Override
    public EnvironmentApplication post(String token, EnvironmentApplication environmentApplication) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, environmentApplication.environment, tx.getUser());
            environmentApplicationRepository.insert(tx, environmentApplication);
            EnvironmentApplication r = environmentApplicationRepository.get(tx, environmentApplication.environment, environmentApplication.application);
            tx.commit();
            return r;
        }
    }

    @Override
    public EnvironmentApplication put(String token, EnvironmentApplication environmentApplication) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, environmentApplication.environment, tx.getUser());
            environmentApplicationRepository.update(tx, environmentApplication);
            EnvironmentApplication r = environmentApplicationRepository.get(tx, environmentApplication.environment, environmentApplication.application);
            tx.commit();
            return r;
        }
    }

    @Override
    public EnvironmentApplication delete(String token, String environment, String application) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            EnvironmentApplication r = environmentApplicationRepository.get(tx, environment, application);
            environmentApplicationRepository.delete(tx, environment, application);
            tx.commit();
            return r;
        }
    }
}
