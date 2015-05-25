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

import apphub.service.v1.api.Build;
import apphub.service.v1.api.IBuildService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.BuildRepository;
import apphub.staff.repository.EnvironmentUserRepository;

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
            environmentUserRepository.check(tx, environment, tx.getUser());
            return buildRepository.get(tx, application, version, environment);
        }
    }

    @Override
    public Build post(String secret, Build build) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            environmentUserRepository.check(tx, build.environment, tx.getUser());
            Build r = buildRepository.insert(tx, build);
            tx.commit();
            return r;
        }
    }

    @Override
    public Build put(String secret, Build build) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            environmentUserRepository.check(tx, build.environment, tx.getUser());
            Build r = buildRepository.update(tx, build);
            tx.commit();
            return r;
        }
    }

    @Override
    public Build delete(String secret, String application, String version, String environment) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            Build r = buildRepository.get(tx, application, version, environment);
            buildRepository.delete(tx, application, version, environment);
            tx.commit();
            return r;
        }
    }
}
