/*
 * Copyright (C) 2014 Dmitry Kotlyarov.
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
import apphub.service.service.v1.ActivationService;
import apphub.service.service.v1.ApplicationService;
import apphub.service.service.v1.ApplicationUserService;
import apphub.service.service.v1.BuildService;
import apphub.service.service.v1.InstanceService;
import apphub.service.service.v1.EnvironmentService;
import apphub.service.service.v1.KeyService;
import apphub.service.service.v1.SecretService;
import apphub.service.service.v1.EnvironmentUserService;
import apphub.service.service.v1.LoginService;
import apphub.service.service.v1.TestService;
import apphub.service.service.v1.TokenService;
import apphub.service.service.v1.UserService;
import apphub.service.service.v1.VersionService;
import apphub.staff.StaffContext;
import apphub.util.property.PropertyUtil;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ServiceContext extends StaffContext {
    protected final Config config = Config.get();
    protected final String tempDirectory = String.format("%s/apphub/service", PropertyUtil.getSystemProperty("java.io.tmpdir"));
    protected final ActivationService activationService = new ActivationService(database, userRepository);
    protected final ApplicationService applicationService = new ApplicationService(database, applicationRepository, applicationUserRepository);
    protected final ApplicationUserService applicationUserService = new ApplicationUserService(database, applicationUserRepository);
    protected final BuildService buildService = new BuildService(database, buildRepository, environmentUserRepository);
    protected final InstanceService instanceService = new InstanceService(database, environmentUserRepository, instanceRepository);
    protected final EnvironmentService environmentService = new EnvironmentService(database, environmentRepository, environmentUserRepository);
    protected final SecretService secretService = new SecretService(database, environmentUserRepository, secretRepository);
    protected final EnvironmentUserService environmentUserService = new EnvironmentUserService(database, environmentUserRepository);
    protected final LoginService loginService = new LoginService(database, userRepository);
    protected final KeyService keyService = new KeyService(database, keyRepository);
    protected final TokenService tokenService = new TokenService(database, tokenRepository);
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

    public InstanceService getInstanceService() {
        return instanceService;
    }

    public EnvironmentService getEnvironmentService() {
        return environmentService;
    }

    public SecretService getSecretService() {
        return secretService;
    }

    public EnvironmentUserService getEnvironmentUserService() {
        return environmentUserService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public KeyService getKeyService() {
        return keyService;
    }

    public TokenService getTokenService() {
        return tokenService;
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
