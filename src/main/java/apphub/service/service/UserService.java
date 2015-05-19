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

import apphub.Config;
import apphub.service.api.IUserService;
import apphub.service.api.User;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserRepository;
import apphub.staff.util.secret.SecretUtil;
import apphub.util.Util;
import apphub.util.io.IOUtil;
import apphub.util.property.PropertyUtil;
import apphub.util.string.StringUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class UserService implements IUserService {
    protected final Database database;
    protected final UserRepository userRepository;
    protected final String activationEmail = IOUtil.getResourceAsString(UserService.class, "activation.email");

    public UserService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
    }

    @Override
    public User get(String secret) {
        try (Transaction tx = new Transaction(database, secret)) {
            return userRepository.get(tx, tx.getUser());
        }
    }

    @Override
    public User put(String password, User user) {
        try (Transaction tx = new Transaction(database, false, null)) {
            if (!userRepository.exists(tx, user.id)) {
                if (!userRepository.existsByEmail(tx, user.email)) {
                    String code = SecretUtil.randomSecret();
                    userRepository.insert(tx, user, StringUtil.toBytes(password), code);
                    sendActivationEmail(user.id, user.name, user.email, code);
                    tx.commit();
                    return user;
                } else {
                    throw new ServerErrorException(String.format("User with email '%s' is found", user.email), Response.Status.FORBIDDEN);
                }
            } else {
                throw new ServerErrorException(String.format("User with id '%s' is found", user.id), Response.Status.FORBIDDEN);
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
            message.setSubject("AppHub user activation notification", Util.CHARSET.name());
            message.setText(String.format(activationEmail, name, id,
                            String.format("https://service.%s/service/activation/%s", Config.get().getDomain(), code)), Util.CHARSET.name());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
