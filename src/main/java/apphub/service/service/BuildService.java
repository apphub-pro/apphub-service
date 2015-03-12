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

import apphub.service.api.Build;
import apphub.service.api.IBuildService;

import javax.ws.rs.HeaderParam;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class BuildService implements IBuildService {
    public BuildService() {
    }

    @Override
    public Build get(@HeaderParam("Secret") String secret, @HeaderParam("Id") String id) {
        return null;
    }

    @Override
    public Build put(@HeaderParam("Secret") String secret, Build build) {
        return null;
    }

    @Override
    public Build post(@HeaderParam("Secret") String secret, Build build) {
        return null;
    }

    @Override
    public Build delete(@HeaderParam("Secret") String secret, @HeaderParam("Id") String id) {
        return null;
    }
}
