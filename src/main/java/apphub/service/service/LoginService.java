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

import apphub.service.api.ILoginService;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserRepository;
import apphub.util.string.StringUtil;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.util.Arrays;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LoginService implements ILoginService {
    protected final Database database;
    protected final UserRepository userRepository;

    public LoginService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
    }

    @Override
    public String get(String id, String password) {
        try (Transaction tx = new Transaction(database)) {
            if (Arrays.equals(StringUtil.toBytes(password), userRepository.getPassword(tx, id))) {
                return userRepository.getSecret(tx, id);
            } else {
                throw new ServerErrorException(String.format("Invalid credentials for user '%s'", id), Response.Status.NOT_FOUND);
            }
        }
    }

    @Override
    public String post(String id, String password) {
        return null;
    }
}
