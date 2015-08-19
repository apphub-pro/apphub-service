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

import apphub.service.v1.api.Instance;
import apphub.service.v1.api.IInstanceService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.InstanceRepository;
import apphub.staff.repository.EnvironmentUserRepository;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class InstanceService implements IInstanceService {
    protected final Database database;
    protected final EnvironmentUserRepository environmentUserRepository;
    protected final InstanceRepository instanceRepository;

    public InstanceService(Database database,
                           EnvironmentUserRepository environmentUserRepository,
                           InstanceRepository instanceRepository) {
        this.database = database;
        this.environmentUserRepository = environmentUserRepository;
        this.instanceRepository = instanceRepository;
    }

    @Override
    public Instance get(String token, String environment, String application) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return instanceRepository.get(tx, environment, application);
        }
    }

    @Override
    public List<Instance> list(String token, String environment) {
        try (Transaction tx = new Transaction(database, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            return instanceRepository.findByEnvironment(tx, environment);
        }
    }

    @Override
    public Instance post(String token, Instance instance) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, instance.environment, tx.getUser());
            instanceRepository.insert(tx, instance);
            Instance r = instanceRepository.get(tx, instance.environment, instance.application);
            tx.commit();
            return r;
        }
    }

    @Override
    public Instance put(String token, Instance instance) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, instance.environment, tx.getUser());
            instanceRepository.update(tx, instance);
            Instance r = instanceRepository.get(tx, instance.environment, instance.application);
            tx.commit();
            return r;
        }
    }

    @Override
    public Instance delete(String token, String environment, String application) {
        try (Transaction tx = new Transaction(database, false, token)) {
            environmentUserRepository.check(tx, environment, tx.getUser());
            Instance r = instanceRepository.get(tx, environment, application);
            instanceRepository.delete(tx, environment, application);
            tx.commit();
            return r;
        }
    }
}
