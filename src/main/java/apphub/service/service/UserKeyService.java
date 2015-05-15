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
        return null;
    }

    @Override
    public List<UserKey> list(String secret, String user) {
        return null;
    }

    @Override
    public UserKey put(String secret, UserKey userKey) {
        return null;
    }

    @Override
    public UserKey post(String secret, UserKey userKey) {
        return null;
    }

    @Override
    public UserKey delete(String secret, String user, String id) {
        return null;
    }
}
