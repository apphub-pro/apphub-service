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
        LinkedHashSet<Object> singletons = new LinkedHashSet<>(16);
        singletons.add(context.getActivationService());
        singletons.add(context.getApplicationService());
        singletons.add(context.getApplicationUserService());
        singletons.add(context.getBuildService());
        singletons.add(context.getEnvironmentApplicationService());
        singletons.add(context.getEnvironmentService());
        singletons.add(context.getEnvironmentUserSecretService());
        singletons.add(context.getEnvironmentUserService());
        singletons.add(context.getLoginService());
        singletons.add(context.getUserKeyService());
        singletons.add(context.getUserSecretService());
        singletons.add(context.getUserService());
        singletons.add(context.getVersionService());
        singletons.add(context.getLdapService());
        singletons.add(context.getTestService());
        return singletons;
    }
}
