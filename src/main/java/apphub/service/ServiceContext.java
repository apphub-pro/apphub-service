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

import apphub.service.service.ApplicationService;
import apphub.service.service.ApplicationsService;
import apphub.service.service.BuildService;
import apphub.service.service.EnvironmentService;
import apphub.service.service.EnvironmentsService;
import apphub.service.ldap.LdapService;
import apphub.service.service.TestService;
import apphub.service.service.UserActivationService;
import apphub.service.service.UserSecretService;
import apphub.service.service.UserService;
import apphub.service.service.VersionService;
import apphub.staff.StaffContext;
import apphub.utility.PropertyUtil;

import java.io.File;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ServiceContext extends StaffContext {
    protected final String tempDirectory = String.format("%s/apphub/service", PropertyUtil.getSystemProperty("java.io.tmpdir"));
    protected final ApplicationService applicationService = new ApplicationService(applicationRepository);
    protected final ApplicationsService applicationsService = new ApplicationsService(applicationRepository);
    protected final BuildService buildService = new BuildService();
    protected final EnvironmentService environmentService = new EnvironmentService();
    protected final EnvironmentsService environmentsService = new EnvironmentsService();
    protected final UserActivationService userActivationService = new UserActivationService(userRepository);
    protected final UserSecretService userSecretService = new UserSecretService(database, userRepository);
    protected final UserService userService = new UserService(database, userRepository);
    protected final VersionService versionService = new VersionService();
    protected final LdapService ldapService = new LdapService(database, userRepository, tempDirectory);
    protected final TestService testService = new TestService();

    public ServiceContext() {
    }

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public ApplicationsService getApplicationsService() {
        return applicationsService;
    }

    public BuildService getBuildService() {
        return buildService;
    }

    public EnvironmentService getEnvironmentService() {
        return environmentService;
    }

    public EnvironmentsService getEnvironmentsService() {
        return environmentsService;
    }

    public UserActivationService getUserActivationService() {
        return userActivationService;
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
