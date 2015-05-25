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

import apphub.service.v1.api.IVersionService;
import apphub.service.v1.api.Version;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.ApplicationUserRepository;
import apphub.staff.repository.VersionRepository;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class VersionService implements IVersionService {
    protected final Database database;
    protected final VersionRepository versionRepository;
    protected final ApplicationUserRepository applicationUserRepository;

    public VersionService(Database database, VersionRepository versionRepository, ApplicationUserRepository applicationUserRepository) {
        this.database = database;
        this.versionRepository = versionRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Version get(String secret, String application, String id) {
        try (Transaction tx = new Transaction(database, secret)) {
            applicationUserRepository.check(tx, application, tx.getUser());
            return versionRepository.get(tx, application, id);
        }
    }

    @Override
    public Version post(String secret, Version version) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            applicationUserRepository.check(tx, version.application, tx.getUser());
            Version r = versionRepository.insert(tx, version);
            tx.commit();
            return r;
        }
    }

    @Override
    public Version put(String secret, Version version) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            applicationUserRepository.check(tx, version.application, tx.getUser());
            Version r = versionRepository.update(tx, version);
            tx.commit();
            return r;
        }
    }
}
