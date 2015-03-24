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

package apphub.service.ldap;

import apphub.staff.database.Database;
import apphub.staff.repository.UserRepository;
import org.forgerock.opendj.ldap.Connections;
import org.forgerock.opendj.ldap.LDAPClientContext;
import org.forgerock.opendj.ldap.LDAPListener;

import java.io.IOException;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LdapService {
    protected final Database database;
    protected final UserRepository userRepository;
    protected final LDAPListener ldapListener;

    public LdapService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
        this.ldapListener = createLdapListener();
    }

    private static LDAPListener createLdapListener() {
        try {
            return new LDAPListener(8389, Connections.<LDAPClientContext>newServerConnectionFactory(new LdapRequestHandler()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
