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

import apphub.service.api.EnvironmentUserSecret;
import apphub.service.api.IEnvironmentUserSecretService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentUserRepository;
import apphub.staff.repository.EnvironmentUserSecretRepository;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class EnvironmentUserSecretService implements IEnvironmentUserSecretService {
    protected final Database database;
    protected final EnvironmentUserRepository environmentUserRepository;
    protected final EnvironmentUserSecretRepository environmentUserSecretRepository;

    public EnvironmentUserSecretService(Database database, EnvironmentUserRepository environmentUserRepository, EnvironmentUserSecretRepository environmentUserSecretRepository) {
        this.database = database;
        this.environmentUserRepository = environmentUserRepository;
        this.environmentUserSecretRepository = environmentUserSecretRepository;
    }

    @Override
    public EnvironmentUserSecret get(String secret, String environment, String id) {
        try (Transaction tx = new Transaction(database, secret)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentUserSecretRepository.get(tx, environment, tx.getUser(), id);
        }
    }

    @Override
    public List<EnvironmentUserSecret> list(String secret, String environment) {
        return null;
    }

    @Override
    public EnvironmentUserSecret put(String secret, EnvironmentUserSecret environmentUserSecret) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            environmentUserRepository.check(tx, environmentUserSecret.environment, tx.getUser());
            EnvironmentUserSecret r = environmentUserSecretRepository.insert(tx, new EnvironmentUserSecret(environmentUserSecret.environment,
                                                                                                           tx.getUser(),
                                                                                                           environmentUserSecret.id,
                                                                                                           environmentUserSecret.createTime,
                                                                                                           environmentUserSecret.secret));
            tx.commit();
            return r;
        }
    }

    @Override
    public EnvironmentUserSecret delete(String secret, String environment, String id) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            EnvironmentUserSecret r = environmentUserSecretRepository.get(tx, environment, tx.getUser(), id);
            environmentUserSecretRepository.delete(tx, environment, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
