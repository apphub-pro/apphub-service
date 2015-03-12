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

package apphub.service.service;

import apphub.service.api.Application;
import apphub.service.api.IApplicationService;
import apphub.staff.repository.ApplicationRepository;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ApplicationService implements IApplicationService {
    protected final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application get(String secret, String id) {
        return null;
    }

    @Override
    public Application put(String secret, Application application) {
        return null;
    }

    @Override
    public Application post(String secret, Application application) {
        return null;
    }
}
