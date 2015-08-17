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

import apphub.service.v1.api.IUserKeyService;
import apphub.service.v1.api.UserKey;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserKeyRepository;

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
    public UserKey get(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            return userKeyRepository.get(tx, tx.getUser(), id);
        }
    }

    @Override
    public String key(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            return userKeyRepository.getKey(tx, tx.getUser(), id);
        }
    }

    @Override
    public List<UserKey> list(String token) {
        try (Transaction tx = new Transaction(database, token)) {
            return userKeyRepository.findByUser(tx, tx.getUser());
        }
    }

    @Override
    public UserKey post(String token, String key, UserKey userKey) {
        try (Transaction tx = new Transaction(database, false, token)) {
            userKeyRepository.insert(tx, new UserKey(tx.getUser(), userKey.id, userKey.createTime), key);
            UserKey r = userKeyRepository.get(tx, userKey.user, userKey.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public UserKey delete(String token, String id) {
        try (Transaction tx = new Transaction(database, false, token)) {
            UserKey r = userKeyRepository.get(tx, tx.getUser(), id);
            userKeyRepository.delete(tx, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
