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

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ServiceApplication extends Application {
    public ServiceApplication() {
    }

    @Override
    public Set<Object> getSingletons() {
        return super.getSingletons();
    }
}
