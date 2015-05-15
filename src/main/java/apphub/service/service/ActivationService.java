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

import apphub.service.api.IActivationService;
import apphub.staff.repository.UserRepository;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ActivationService implements IActivationService {
    protected final UserRepository userRepository;

    public ActivationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String get(String key) {
        return "ACTIVATION IS SUCCESSFUL";
    }
}
