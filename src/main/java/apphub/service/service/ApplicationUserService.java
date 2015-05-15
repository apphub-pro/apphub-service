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

import apphub.service.api.ApplicationUser;
import apphub.service.api.IApplicationUserService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.ApplicationUserRepository;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ApplicationUserService implements IApplicationUserService {
    protected final Database database;
    protected final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserService(Database database, ApplicationUserRepository applicationUserRepository) {
        this.database = database;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public ApplicationUser get(String secret, String application, String user) {
        try (Transaction tx = new Transaction(database, secret)) {
            if (applicationUserRepository.exists(tx, application, tx.getUser())) {
                return applicationUserRepository.get(tx, application, user);
            } else {
                throw new IllegalArgumentException(String.format("Application user with application '%s' and user '%s' is not found", application, tx.getUser()));
            }
        }
    }

    @Override
    public List<ApplicationUser> list(String secret, String application) {
        return null;
    }

    @Override
    public ApplicationUser put(String secret, ApplicationUser applicationUser) {
        return null;
    }

    @Override
    public ApplicationUser post(String secret, ApplicationUser applicationUser) {
        return null;
    }
}
