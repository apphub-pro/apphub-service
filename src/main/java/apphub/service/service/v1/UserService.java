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

import apphub.Config;
import apphub.service.v1.api.IUserService;
import apphub.service.v1.api.User;
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
import javax.ws.rs.ForbiddenException;

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
    public User post(String password, User user) {
        try (Transaction tx = new Transaction(database, false, null)) {
            if (!userRepository.exists(tx, user.id)) {
                if (!userRepository.existsByEmail(tx, user.email)) {
                    String code = SecretUtil.randomSecret();
                    userRepository.insert(tx, user, StringUtil.toBytes(password), code);
// DTP:                    sendActivationEmail(user.id, user.name, user.email, code);
// ETP [BEGIN]:
                    user.url = String.format("https://service.%s/service/v1/activation/%s", Config.get().getDomain(), code);
// ETP [END]:
                    tx.commit();
                    return user;
                } else {
                    throw new ForbiddenException(String.format("User with email '%s' is found", user.email));
                }
            } else {
                throw new ForbiddenException(String.format("User with id '%s' is found", user.id));
            }
        }
    }

    @Override
    public User put(String secret, User user) {
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
                            String.format("https://service.%s/service/v1/activation/%s", Config.get().getDomain(), code)), Util.CHARSET.name());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
