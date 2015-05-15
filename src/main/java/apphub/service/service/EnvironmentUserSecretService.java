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
        return null;
    }

    @Override
    public List<EnvironmentUserSecret> list(String secret, String environment) {
        return null;
    }

    @Override
    public EnvironmentUserSecret put(String secret, EnvironmentUserSecret environmentUserSecret) {
        return null;
    }

    @Override
    public EnvironmentUserSecret post(String secret, EnvironmentUserSecret environmentUserSecret) {
        return null;
    }

    @Override
    public EnvironmentUserSecret delete(String secret, String environment, String id) {
        return null;
    }
}
