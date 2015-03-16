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
import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LdapService {
    protected final Database database;
    protected final UserRepository userRepository;
    protected final DirectoryService directoryService;
    protected final LdapAuthenticator ldapAuthenticator;
    protected final LdapServer ldapServer;

    public LdapService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
        this.directoryService = createDirectoryService();
        this.ldapAuthenticator = new LdapAuthenticator(database, userRepository, directoryService);
        this.ldapServer = new LdapServer();

        try {
            ldapServer.setTransports(new TcpTransport(8389));
            ldapServer.setDirectoryService(directoryService);
            ldapServer.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Database getDatabase() {
        return database;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public DirectoryService getDirectoryService() {
        return directoryService;
    }

    public LdapAuthenticator getLdapAuthenticator() {
        return ldapAuthenticator;
    }

    public LdapServer getLdapServer() {
        return ldapServer;
    }

    private static DirectoryService createDirectoryService() {
        try {
            return new DefaultDirectoryService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
