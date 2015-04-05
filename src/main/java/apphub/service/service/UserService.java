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

import apphub.service.api.Application;
import apphub.service.api.Environment;
import apphub.service.api.IUserService;
import apphub.service.api.User;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserRepository;
import apphub.staff.utility.SecretUtil;
import apphub.utility.IOUtil;
import apphub.utility.PropertyUtil;
import apphub.utility.StringUtil;
import apphub.utility.TimeUtil;
import apphub.utility.Util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class UserService implements IUserService {
    protected final Database database;
    protected final UserRepository userRepository;
    protected final String userActivationEmail = IOUtil.getResourceAsString(UserService.class, "user-activation.email");

    public UserService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
    }

    @Override
    public User get(String secret, String id) {
        try (Transaction tx = new Transaction(database, id)) {
            return userRepository.getById(tx, id);
        }
    }

    @Override
    public User put(String password, User user) {
        try (Transaction tx = new Transaction(database, false, null)) {
            if (!userRepository.existById(tx, user.id)) {
                if (!userRepository.existByEmail(tx, user.email)) {
                    userRepository.insert(tx, user, StringUtil.toBytes(password), SecretUtil.randomSecret());
//                    sendActivationEmail(user.id, user.name, user.email);
                    tx.commit();
                    return user;
                } else {
                    throw new ServerErrorException(String.format("User with email '%s' is found", user.email), Response.Status.FOUND);
                }
            } else {
                throw new ServerErrorException(String.format("User with id '%s' is found", user.id), Response.Status.FOUND);
            }
        }
    }

    @Override
    public User post(String secret, User user) {
        return null;
    }

    private void sendActivationEmail(String id, String name, String email, String code) {
        Session session = Session.getDefaultInstance(PropertyUtil.toProperties(new String[][] {{"mail.smtp.host", "localhost"}}));
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("staff@apphub.pro"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("APPHUB USER ACTIVATION NOTIFICATION", Util.CHARSET.name());
            message.setText(String.format(userActivationEmail, name, id,
                            String.format("https://service.dev.apphub.pro/service/user/activation/%s", code)), Util.CHARSET.name());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
