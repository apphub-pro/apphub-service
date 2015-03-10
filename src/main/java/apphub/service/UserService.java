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

package apphub.service;

import apphub.service.api.Application;
import apphub.service.api.Environment;
import apphub.service.api.IUserService;
import apphub.service.api.User;
import apphub.staff.repository.UserRepository;
import apphub.staff.utility.SecretUtil;
import apphub.utility.IOUtil;
import apphub.utility.PropertyUtil;
import apphub.utility.TimeUtil;
import apphub.utility.Util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class UserService implements IUserService {
    protected final UserRepository userRepository;
    protected final String userActivationEmail = IOUtil.getResourceAsString(UserService.class, "user-activation.email");

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User get(String secret, String id) {
        Timestamp ts = TimeUtil.currentTime();
        List<Application> applications = Collections.emptyList();
        List<Environment> environments = Collections.emptyList();
        User user = new User(id, "Dmitry Kotlyarov", "kotlyarov.dmitry@gmail.com", "https://www.apphub.pro/", "AppHub Pro", "Saint-Petersburg", null,
                             ts, ts, applications, environments);
        return user;
    }

    @Override
    public User put(String password, User user) {
        Session session = Session.getDefaultInstance(PropertyUtil.toProperties(new String[][] {{"mail.smtp.host", "localhost"}}));
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("staff@apphub.pro"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.email));
            message.setSubject("APPHUB USER ACTIVATION NOTIFICATION", Util.CHARSET.name());
            message.setText(String.format(userActivationEmail, user.name, user.id,
                            String.format("https://service.dev.apphub.pro/service/user/activation/%s", SecretUtil.randomSecret())), Util.CHARSET.name());
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User post(String secret, User user) {
        return null;
    }
}
