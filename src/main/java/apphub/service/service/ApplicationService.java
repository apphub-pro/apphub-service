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

import apphub.service.api.Application;
import apphub.service.api.ApplicationUser;
import apphub.service.api.IApplicationService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.ApplicationRepository;
import apphub.staff.repository.ApplicationUserRepository;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ApplicationService implements IApplicationService {
    protected final Database database;
    protected final ApplicationRepository applicationRepository;
    protected final ApplicationUserRepository applicationUserRepository;

    public ApplicationService(Database database, ApplicationRepository applicationRepository, ApplicationUserRepository applicationUserRepository) {
        this.database = database;
        this.applicationRepository = applicationRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Application get(String secret, String id) {
        try (Transaction tx = new Transaction(database, secret)) {
            applicationUserRepository.check(tx, id, tx.getUser());
            return applicationRepository.get(tx, id);
        }
    }

    @Override
    public List<Application> list(String secret) {
        return null;
    }

    @Override
    public Application put(String secret, Application application) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            Application r = applicationRepository.insert(tx, application);
            applicationUserRepository.insert(tx, new ApplicationUser(application.id,
                                                                     tx.getUser(),
                                                                     tx.getTime(),
                                                                     tx.getUser(),
                                                                     tx.getTime(),
                                                                     tx.getUser(),
                                                                     true));
            tx.commit();
            return r;
        }
    }

    @Override
    public Application post(String secret, Application application) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            applicationUserRepository.check(tx, application.id, tx.getUser());
            Application r = applicationRepository.update(tx, application);
            tx.commit();
            return r;
        }
    }
}
