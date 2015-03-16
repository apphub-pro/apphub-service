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
import org.apache.directory.api.ldap.model.constants.AuthenticationLevel;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapAuthenticationException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.LdapPrincipal;
import org.apache.directory.server.core.api.interceptor.context.BindOperationContext;
import org.apache.directory.server.core.authn.AuthenticationInterceptor;
import org.apache.directory.server.core.authn.Authenticator;
import org.apache.directory.server.i18n.I18n;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LdapAuthenticator implements Authenticator {
    protected final Database database;
    protected final UserRepository userRepository;
    protected final DirectoryService directoryService;

    public LdapAuthenticator(Database database, UserRepository userRepository, DirectoryService directoryService) {
        this.database = database;
        this.userRepository = userRepository;
        this.directoryService = directoryService;

        try {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor();
            interceptor.setAuthenticators(new Authenticator[] {this});
            directoryService.addLast(interceptor);
        } catch (LdapException e) {
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

    @Override
    public AuthenticationLevel getAuthenticatorType() {
        return AuthenticationLevel.SIMPLE;
    }

    @Override
    public void init(DirectoryService directoryService) throws LdapException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void invalidateCache(Dn rdns) {
    }

    @Override
    public LdapPrincipal authenticate(BindOperationContext bindOperationContext) throws Exception {
        byte[] passwordData = bindOperationContext.getCredentials();
        String password = new String(passwordData);
        if (password.equals("dmktv")) {
            return new LdapPrincipal();
        } else {
            String message = I18n.err(I18n.ERR_230, bindOperationContext.getDn().getName());
            throw new LdapAuthenticationException(message);
        }
    }

    @Override
    public void checkPwdPolicy(Entry attributes) throws LdapException {
    }
}
