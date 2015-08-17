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

import apphub.service.v1.api.Secret;
import apphub.service.v1.api.ISecretService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentUserRepository;
import apphub.staff.repository.SecretRepository;
import apphub.staff.util.secret.SecretUtil;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class SecretService implements ISecretService {
    protected final Database database;
    protected final EnvironmentUserRepository environmentUserRepository;
    protected final SecretRepository secretRepository;

    public SecretService(Database database, EnvironmentUserRepository environmentUserRepository, SecretRepository secretRepository) {
        this.database = database;
        this.environmentUserRepository = environmentUserRepository;
        this.secretRepository = secretRepository;
    }

    @Override
    public Secret get(String token, String environment, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return secretRepository.get(tx, environment, tx.getUser(), id);
        }
    }

    @Override
    public String secret(String token, String environment, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return secretRepository.getSecret(tx, environment, tx.getUser(), id);
        }
    }

    @Override
    public List<Secret> list(String token, String environment) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return secretRepository.findByEnvironmentAndUser(tx, environment, tx.getUser());
        }
    }

    @Override
    public Secret post(String token, Secret secret) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, secret.environment, tx.getUser());
            secretRepository.insert(tx, new Secret(secret.environment, tx.getUser(), secret.id, secret.createTime), SecretUtil.randomSecret());
            Secret r = secretRepository.get(tx, secret.environment, secret.user, secret.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public Secret delete(String token, String environment, String id) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            Secret r = secretRepository.get(tx, environment, tx.getUser(), id);
            secretRepository.delete(tx, environment, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
