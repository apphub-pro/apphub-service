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

package apphub.service.service.v1;

import apphub.service.v1.api.IActivationService;
import apphub.service.v1.api.User;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.UserRepository;
import apphub.staff.util.secret.SecretUtil;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class ActivationService implements IActivationService {
    protected final Database database;
    protected final UserRepository userRepository;

    public ActivationService(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;
    }

    @Override
    public String get(String code) {
        try (Transaction tx = new Transaction(database, false, null)) {
            User user = userRepository.getByCode(tx, code);
            if (userRepository.findToken(tx, user.id) == null) {
                userRepository.updateToken(tx, user.id, SecretUtil.randomSecret());
                tx.commit();
            }
            return "ACTIVATION IS SUCCESSFUL";
        }
    }
}
