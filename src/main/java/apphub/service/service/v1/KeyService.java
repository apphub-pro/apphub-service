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

import apphub.service.v1.api.IKeyService;
import apphub.service.v1.api.Key;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.KeyRepository;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class KeyService implements IKeyService {
    protected final Database database;
    protected final KeyRepository keyRepository;

    public KeyService(Database database, KeyRepository keyRepository) {
        this.database = database;
        this.keyRepository = keyRepository;
    }

    @Override
    public Key get(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            return keyRepository.get(tx, tx.getUser(), id);
        }
    }

    @Override
    public String key(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            return keyRepository.getKey(tx, tx.getUser(), id);
        }
    }

    @Override
    public List<Key> list(String token) {
        try (Transaction tx = new Transaction(database, token)) {
            return keyRepository.findByUser(tx, tx.getUser());
        }
    }

    @Override
    public Key post(String token, String key, Key keyInfo) {
        try (Transaction tx = new Transaction(database, false, token)) {
            keyRepository.insert(tx, new Key(tx.getUser(), keyInfo.id, keyInfo.createTime), key);
            Key r = keyRepository.get(tx, keyInfo.user, keyInfo.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public Key delete(String token, String id) {
        try (Transaction tx = new Transaction(database, false, token)) {
            Key r = keyRepository.get(tx, tx.getUser(), id);
            keyRepository.delete(tx, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
