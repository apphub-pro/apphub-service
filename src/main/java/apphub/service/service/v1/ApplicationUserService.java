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

import apphub.service.v1.api.ApplicationUser;
import apphub.service.v1.api.IApplicationUserService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.ApplicationUserRepository;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
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
            applicationUserRepository.check(tx, application, tx.getUser());
            return applicationUserRepository.get(tx, application, user);
        }
    }

    @Override
    public List<ApplicationUser> list(String secret, String application) {
        try (Transaction tx = new Transaction(database, secret)) {
            applicationUserRepository.check(tx, application, tx.getUser());
            return applicationUserRepository.findByApplication(tx, application);
        }
    }

    @Override
    public ApplicationUser post(String secret, ApplicationUser applicationUser) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            ApplicationUser user = applicationUserRepository.get(tx, applicationUser.application, tx.getUser());
            if (user.admin) {
                applicationUserRepository.insert(tx, applicationUser);
                ApplicationUser r = applicationUserRepository.get(tx, applicationUser.application, applicationUser.user);
                tx.commit();
                return r;
            } else {
                throw new ServerErrorException(String.format("Application user with application '%s' and user '%s' does not have an admin privilege", applicationUser.application, tx.getUser()), Response.Status.FORBIDDEN);
            }
        }
    }

    @Override
    public ApplicationUser put(String secret, ApplicationUser applicationUser) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            ApplicationUser user = applicationUserRepository.get(tx, applicationUser.application, tx.getUser());
            if (user.admin) {
                applicationUserRepository.update(tx, applicationUser);
                ApplicationUser r = applicationUserRepository.get(tx, applicationUser.application, applicationUser.user);
                tx.commit();
                return r;
            } else {
                throw new ServerErrorException(String.format("Application user with application '%s' and user '%s' does not have an admin privilege", applicationUser.application, tx.getUser()), Response.Status.FORBIDDEN);
            }
        }
    }
}
