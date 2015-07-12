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

import apphub.service.v1.api.EnvironmentUserSecret;
import apphub.service.v1.api.IEnvironmentUserSecretService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentUserRepository;
import apphub.staff.repository.EnvironmentUserSecretRepository;
import apphub.staff.util.secret.SecretUtil;

import javax.ws.rs.HeaderParam;
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
    public String secret(String secret, String environment, String id) {
        try (Transaction tx = new Transaction(database, secret)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentUserSecretRepository.getSecret(tx, environment, tx.getUser(), id);
        }
    }

    @Override
    public List<EnvironmentUserSecret> list(String secret, String environment) {
        try (Transaction tx = new Transaction(database, secret)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentUserSecretRepository.findByEnvironmentAndUser(tx, environment, tx.getUser());
        }
    }

    @Override
    public EnvironmentUserSecret post(String secret, EnvironmentUserSecret environmentUserSecret) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            environmentUserRepository.check(tx, environmentUserSecret.environment, tx.getUser());
            environmentUserSecretRepository.insert(tx, new EnvironmentUserSecret(environmentUserSecret.environment, tx.getUser(), environmentUserSecret.id, environmentUserSecret.createTime), SecretUtil.randomSecret());
            EnvironmentUserSecret r = environmentUserSecretRepository.get(tx, environmentUserSecret.environment, environmentUserSecret.user, environmentUserSecret.id);
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
