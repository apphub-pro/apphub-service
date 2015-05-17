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

import apphub.Config;
import apphub.service.ldap.LdapService;
import apphub.service.service.ActivationService;
import apphub.service.service.ApplicationService;
import apphub.service.service.ApplicationUserService;
import apphub.service.service.BuildService;
import apphub.service.service.EnvironmentApplicationService;
import apphub.service.service.EnvironmentService;
import apphub.service.service.EnvironmentUserSecretService;
import apphub.service.service.EnvironmentUserService;
import apphub.service.service.LoginService;
import apphub.service.service.TestService;
import apphub.service.service.UserKeyService;
import apphub.service.service.UserSecretService;
import apphub.service.service.UserService;
import apphub.service.service.VersionService;
import apphub.staff.StaffContext;
import apphub.util.PropertyUtil;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ServiceContext extends StaffContext {
    protected final Config config = Config.get();
    protected final String tempDirectory = String.format("%s/apphub/service", PropertyUtil.getSystemProperty("java.io.tmpdir"));
    protected final ActivationService activationService = new ActivationService(userRepository);
    protected final ApplicationService applicationService = new ApplicationService(database, applicationRepository, applicationUserRepository);
    protected final ApplicationUserService applicationUserService = new ApplicationUserService(database, applicationUserRepository);
    protected final BuildService buildService = new BuildService(database, buildRepository, environmentUserRepository);
    protected final EnvironmentApplicationService environmentApplicationService = new EnvironmentApplicationService(database, environmentRepository, environmentUserRepository, environmentApplicationRepository);
    protected final EnvironmentService environmentService = new EnvironmentService(database, environmentRepository, environmentUserRepository);
    protected final EnvironmentUserSecretService environmentUserSecretService = new EnvironmentUserSecretService(database, environmentUserRepository, environmentUserSecretRepository);
    protected final EnvironmentUserService environmentUserService = new EnvironmentUserService(database, environmentUserRepository);
    protected final LoginService loginService = new LoginService(database, userRepository);
    protected final UserKeyService userKeyService = new UserKeyService(database, userKeyRepository);
    protected final UserSecretService userSecretService = new UserSecretService(database, userRepository);
    protected final UserService userService = new UserService(database, userRepository);
    protected final VersionService versionService = new VersionService(database, versionRepository, applicationUserRepository);
    protected final LdapService ldapService = new LdapService(database, userRepository);
    protected final TestService testService = new TestService();

    public ServiceContext() {
    }

    public Config getConfig() {
        return config;
    }

    public String getTempDirectory() {
        return tempDirectory;
    }

    public ActivationService getActivationService() {
        return activationService;
    }

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public ApplicationUserService getApplicationUserService() {
        return applicationUserService;
    }

    public BuildService getBuildService() {
        return buildService;
    }

    public EnvironmentApplicationService getEnvironmentApplicationService() {
        return environmentApplicationService;
    }

    public EnvironmentService getEnvironmentService() {
        return environmentService;
    }

    public EnvironmentUserSecretService getEnvironmentUserSecretService() {
        return environmentUserSecretService;
    }

    public EnvironmentUserService getEnvironmentUserService() {
        return environmentUserService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public UserKeyService getUserKeyService() {
        return userKeyService;
    }

    public UserSecretService getUserSecretService() {
        return userSecretService;
    }

    public UserService getUserService() {
        return userService;
    }

    public VersionService getVersionService() {
        return versionService;
    }

    public LdapService getLdapService() {
        return ldapService;
    }

    public TestService getTestService() {
        return testService;
    }
}
