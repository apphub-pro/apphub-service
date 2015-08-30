/*
 * Copyright (C) 2014 Dmitry Kotlyarov.
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

import apphub.service.v1.api.Application;
import apphub.service.v1.api.ApplicationUser;
import apphub.service.v1.api.IApplicationService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.ApplicationRepository;
import apphub.staff.repository.ApplicationUserRepository;

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
    public Application get(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            applicationUserRepository.check(tx, id, tx.getUser());
            return applicationRepository.get(tx, id);
        }
    }

    @Override
    public List<Application> list(String token) {
        try (Transaction tx = new Transaction(database, token)) {
            return applicationRepository.findByUser(tx, tx.getUser());
        }
    }

    @Override
    public Application post(String token, Application application) {
        try (Transaction tx = new Transaction(database, false, token)) {
            applicationRepository.insert(tx, application);
            applicationUserRepository.insert(tx, new ApplicationUser(application.id,
                                                                     tx.getUser(),
                                                                     tx.getTime(),
                                                                     tx.getUser(),
                                                                     tx.getTime(),
                                                                     tx.getUser(),
                                                                     true));
            Application r = applicationRepository.get(tx, application.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public Application put(String token, Application application) {
        try (Transaction tx = new Transaction(database, false, token)) {
            applicationUserRepository.check(tx, application.id, tx.getUser());
            applicationRepository.update(tx, application);
            Application r = applicationRepository.get(tx, application.id);
            tx.commit();
            return r;
        }
    }
}
