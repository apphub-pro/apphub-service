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

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
@ApplicationPath("/service")
public class ServiceApplication extends Application {
    protected final ServiceContext context = new ServiceContext();

    public ServiceApplication() {
    }

    @Override
    public Set<Object> getSingletons() {
        LinkedHashSet<Object> singletons = new LinkedHashSet<>(12);
        singletons.add(context.getApplicationService());
        singletons.add(context.getApplicationsService());
        singletons.add(context.getApplicationUserService());
        singletons.add(context.getApplicationUsersService());
        singletons.add(context.getBuildService());
        singletons.add(context.getEnvironmentService());
        singletons.add(context.getEnvironmentsService());
        singletons.add(context.getUserActivationService());
        singletons.add(context.getUserSecretService());
        singletons.add(context.getUserService());
        singletons.add(context.getVersionService());
        singletons.add(context.getTestService());
        return singletons;
    }
}
