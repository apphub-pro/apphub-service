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

import apphub.service.api.IUserKeyService;
import apphub.service.api.UserKey;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserKeyRepository;

import javax.ws.rs.HeaderParam;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class UserKeyService implements IUserKeyService {
    protected final Database database;
    protected final UserKeyRepository userKeyRepository;

    public UserKeyService(Database database, UserKeyRepository userKeyRepository) {
        this.database = database;
        this.userKeyRepository = userKeyRepository;
    }

    @Override
    public UserKey get(String secret, String user, String id) {
        try (Transaction tx = new Transaction(database, secret)) {
            return userKeyRepository.get(tx, tx.getUser(), id);
        }
    }

    @Override
    public List<UserKey> list(String secret, String user) {
        return null;
    }

    @Override
    public UserKey put(String secret, UserKey userKey) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            UserKey r = userKeyRepository.insert(tx, new UserKey(tx.getUser(),
                                                                 userKey.id,
                                                                 userKey.createTime,
                                                                 userKey.key));
            tx.commit();
            return r;
        }
    }

    @Override
    public UserKey delete(String secret, String user, String id) {
        try (Transaction tx = new Transaction(database, false, secret)) {
            UserKey r = userKeyRepository.get(tx, tx.getUser(), id);
            userKeyRepository.delete(tx, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
