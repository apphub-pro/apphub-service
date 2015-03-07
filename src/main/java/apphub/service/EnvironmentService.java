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

import apphub.service.api.Environment;
import apphub.service.api.IEnvironmentService;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class EnvironmentService implements IEnvironmentService {
    public EnvironmentService() {
    }

    @Override
    public Environment get(String secret, String id) {
        return null;
    }

    @Override
    public Environment put(String secret, Environment environment) {
        return null;
    }

    @Override
    public Environment post(String secret, Environment environment) {
        return null;
    }
}
