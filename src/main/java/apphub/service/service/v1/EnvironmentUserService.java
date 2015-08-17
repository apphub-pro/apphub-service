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

import apphub.service.v1.api.EnvironmentUser;
import apphub.service.v1.api.IEnvironmentUserService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentUserRepository;

import javax.ws.rs.ForbiddenException;
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
    public EnvironmentUser get(String token, String environment, String user) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentUserRepository.get(tx, environment, user);
        }
    }

    @Override
    public List<EnvironmentUser> list(String token, String environment) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return environmentUserRepository.findByEnvironment(tx, environment);
        }
    }

    @Override
    public EnvironmentUser post(String token, EnvironmentUser environmentUser) {
        try (Transaction tx = new Transaction(database, false, token)) {
            EnvironmentUser user = environmentUserRepository.get(tx, environmentUser.environment, tx.getUser());
            if (user.admin) {
                environmentUserRepository.insert(tx, environmentUser);
                EnvironmentUser r = environmentUserRepository.get(tx, environmentUser.environment, environmentUser.user);
                tx.commit();
                return r;
            } else {
                throw new ForbiddenException(String.format("Environment user with environment '%s' and user '%s' does not have an admin privilege", environmentUser.environment, tx.getUser()));
            }
        }
    }

    @Override
    public EnvironmentUser put(String token, EnvironmentUser environmentUser) {
        try (Transaction tx = new Transaction(database, false, token)) {
            EnvironmentUser user = environmentUserRepository.get(tx, environmentUser.environment, tx.getUser());
            if (user.admin) {
                environmentUserRepository.update(tx, environmentUser);
                EnvironmentUser r = environmentUserRepository.get(tx, environmentUser.environment, environmentUser.user);
                tx.commit();
                return r;
            } else {
                throw new ForbiddenException(String.format("Environment user with environment '%s' and user '%s' does not have an admin privilege", environmentUser.environment, tx.getUser()));
            }
        }
    }

    @Override
    public EnvironmentUser delete(String token, String environment, String user) {
        try (Transaction tx = new Transaction(database, false, token)) {
            EnvironmentUser user1 = environmentUserRepository.get(tx, environment, tx.getUser());
            if (user1.admin) {
                EnvironmentUser r = environmentUserRepository.get(tx, environment, user);
                environmentUserRepository.delete(tx, environment, user);
                tx.commit();
                return r;
            } else {
                throw new ForbiddenException(String.format("Environment user with environment '%s' and user '%s' does not have an admin privilege", environment, tx.getUser()));
            }
        }
    }
}
