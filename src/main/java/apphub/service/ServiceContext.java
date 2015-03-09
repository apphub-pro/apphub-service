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

import apphub.staff.StaffContext;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ServiceContext extends StaffContext {
    protected final ApplicationService applicationService = new ApplicationService(applicationRepository);
    protected final ApplicationsService applicationsService = new ApplicationsService(applicationRepository);
    protected final BuildService buildService = new BuildService();
    protected final EnvironmentService environmentService = new EnvironmentService();
    protected final EnvironmentsService environmentsService = new EnvironmentsService();
    protected final UserActivationService userActivationService = new UserActivationService(userRepository);
    protected final UserSecretService userSecretService = new UserSecretService();
    protected final UserService userService = new UserService(userRepository);
    protected final VersionService versionService = new VersionService();
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

    public TestService getTestService() {
        return testService;
    }
}
