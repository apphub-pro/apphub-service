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

import apphub.service.api.IUserSecretService;
import apphub.service.api.UserSecret;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.model.tables.TUser;
import apphub.staff.repository.UserRepository;

import javax.ws.rs.HeaderParam;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class UserSecretService implements IUserSecretService {
    protected final Database database;
    protected final UserRepository userRepository;

    public UserSecretService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
    }

    @Override
    public UserSecret get(String secret, String user, String id) {
        return null;
    }

    @Override
    public List<UserSecret> list(String secret, String user) {
        return null;
    }

    @Override
    public UserSecret put(String secret, UserSecret userSecret) {
        return null;
    }

    @Override
    public UserSecret delete(String secret, String user, String id) {
        return null;
    }
}
