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

import apphub.service.v1.api.Environment;
import apphub.service.v1.api.EnvironmentUser;
import apphub.service.v1.api.IEnvironmentService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.EnvironmentRepository;
import apphub.staff.repository.EnvironmentUserRepository;

import javax.ws.rs.ForbiddenException;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class EnvironmentService implements IEnvironmentService {
    protected final Database database;
    protected final EnvironmentRepository environmentRepository;
    protected final EnvironmentUserRepository environmentUserRepository;

    public EnvironmentService(Database database, EnvironmentRepository environmentRepository, EnvironmentUserRepository environmentUserRepository) {
        this.database = database;
        this.environmentRepository = environmentRepository;
        this.environmentUserRepository = environmentUserRepository;
    }

    @Override
    public Environment get(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, id, tx.getUser());
            return environmentRepository.get(tx, id);
        }
    }

    @Override
    public List<Environment> list(String token) {
        try (Transaction tx = new Transaction(database, token)) {
            return environmentRepository.findByUser(tx, tx.getUser());
        }
    }

    @Override
    public Environment post(String token, String environmentSecret, Environment environment) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentRepository.insert(tx, environment, environmentSecret);
            environmentUserRepository.insert(tx, new EnvironmentUser(environment.id,
                                                                     tx.getUser(),
                                                                     tx.getTime(),
                                                                     tx.getUser(),
                                                                     tx.getTime(),
                                                                     tx.getUser(),
                                                                     true));
            Environment r = environmentRepository.get(tx, environment.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public Environment put(String token, Environment environment) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, environment.id, tx.getUser());
            environmentRepository.update(tx, environment);
            Environment r = environmentRepository.get(tx, environment.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public Environment delete(String token, String id) {
        try (Transaction tx = new Transaction(database, false, token)) {
            EnvironmentUser user = environmentUserRepository.get(tx, id, tx.getUser());
            if (user.admin) {
                Environment r = environmentRepository.get(tx, id);
                environmentRepository.delete(tx, id);
                tx.commit();
                return r;
            } else {
                throw new ForbiddenException(String.format("Environment user with environment '%s' and user '%s' does not have an admin privilege", id, tx.getUser()));
            }
        }
    }
}
