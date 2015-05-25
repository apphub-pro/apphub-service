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

import apphub.service.v1.api.IUserSecretService;
import apphub.service.v1.api.UserSecret;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserSecretRepository;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class UserSecretService implements IUserSecretService {
    protected final Database database;
    protected final UserSecretRepository userSecretRepository;

    public UserSecretService(Database database, UserSecretRepository userSecretRepository) {
        this.database = database;
        this.userSecretRepository = userSecretRepository;
    }

    @Override
    public UserSecret get(String secret, String user, String id) {
        try (Transaction tx = new Transaction(database, secret)) {
            return userSecretRepository.get(tx, tx.getUser(), id);
        }
    }

    @Override
    public List<UserSecret> list(String secret, String user) {
        return null;
    }

    @Override
    public UserSecret post(String secret, UserSecret userSecret) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            UserSecret r = userSecretRepository.insert(tx, new UserSecret(tx.getUser(),
                                                                          userSecret.id,
                                                                          userSecret.createTime,
                                                                          userSecret.secret));
            tx.commit();
            return r;
        }
    }

    @Override
    public UserSecret delete(String secret, String user, String id) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            UserSecret r = userSecretRepository.get(tx, tx.getUser(), id);
            userSecretRepository.delete(tx, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
