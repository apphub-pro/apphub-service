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
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.schema.SchemaManager;
import org.apache.directory.api.ldap.schemaextractor.SchemaLdifExtractor;
import org.apache.directory.api.ldap.schemaextractor.impl.DefaultSchemaLdifExtractor;
import org.apache.directory.api.ldap.schemamanager.impl.DefaultSchemaManager;
import org.apache.directory.server.constants.ServerDNConstants;
import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.DnFactory;
import org.apache.directory.server.core.api.InstanceLayout;
import org.apache.directory.server.core.api.schema.SchemaPartition;
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition;
import org.apache.directory.server.core.partition.ldif.LdifPartition;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;

import java.io.File;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LdapService {
    protected final Database database;
    protected final UserRepository userRepository;
    protected final String tempDirectory;
    protected final DirectoryService directoryService;
    protected final LdapAuthenticator ldapAuthenticator;
    protected final LdapServer ldapServer;

    public LdapService(Database database, UserRepository userRepository, String tempDirectory) {
        this.database = database;
        this.userRepository = userRepository;
        this.tempDirectory = String.format("%s/ldap", tempDirectory);
        this.directoryService = createDirectoryService(database, this.tempDirectory);
        this.ldapAuthenticator = new LdapAuthenticator(database, userRepository, directoryService);
        this.ldapServer = createLdapServer(directoryService);

        try {
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

    public String getTempDirectory() {
        return tempDirectory;
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

    private static DirectoryService createDirectoryService(Database database, String tempDirectory) {
        File dir = new File(tempDirectory);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException(String.format("Temp directory '%s' could not be created", tempDirectory));
            }
        }
        try {
            InstanceLayout layout = new InstanceLayout(dir);
            SchemaManager schemaManager = new DefaultSchemaManager();

            File schemaPartitionDirectory = new File(layout.getPartitionsDirectory(), "schema");
            if (!schemaPartitionDirectory.exists()) {
                SchemaLdifExtractor extractor = new DefaultSchemaLdifExtractor(layout.getPartitionsDirectory());
                extractor.extractOrCopy();
            }

            DirectoryService service = new DefaultDirectoryService();
            service.setInstanceLayout(layout);
            service.setSchemaManager(schemaManager);

            DnFactory dnFactory = service.getDnFactory();

            LdifPartition ldifPartition = new LdifPartition(schemaManager, dnFactory);
            ldifPartition.setPartitionPath(schemaPartitionDirectory.toURI());

            SchemaPartition schemaPartition = new SchemaPartition(schemaManager);
            schemaPartition.setWrappedPartition(ldifPartition);
            service.setSchemaPartition(schemaPartition);

            JdbmPartition systemPartition = new JdbmPartition(schemaManager, dnFactory);
            systemPartition.setId("system");
            systemPartition.setPartitionPath(new File(layout.getPartitionsDirectory(), systemPartition.getId()).toURI());
            systemPartition.setSuffixDn(new Dn(ServerDNConstants.SYSTEM_DN));
            service.setSystemPartition(systemPartition);

            service.getChangeLog().setEnabled(false);
            service.setDenormalizeOpAttrsEnabled(true);

            service.addPartition(new LdapPartition(database, schemaManager, "apphub", new Dn("dc=apphub")));

            service.startup();
            return service;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static LdapServer createLdapServer(DirectoryService directoryService) {
        try {
            LdapServer server = new LdapServer();
            server.setTransports(new TcpTransport(8389));
            server.setDirectoryService(directoryService);
            return server;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
