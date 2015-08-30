/*
 * Copyright (C) 2014 Dmitry Kotlyarov.
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

import apphub.service.v1.api.ITokenService;
import apphub.service.v1.api.Token;
import apphub.staff.database.Database;
import apphub.staff.database.Transaction;
import apphub.staff.repository.TokenRepository;
import apphub.staff.util.secret.SecretUtil;

import java.util.List;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class TokenService implements ITokenService {
    protected final Database database;
    protected final TokenRepository tokenRepository;

    public TokenService(Database database, TokenRepository tokenRepository) {
        this.database = database;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token get(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            return tokenRepository.get(tx, tx.getUser(), id);
        }
    }

    @Override
    public String token(String token, String id) {
        try (Transaction tx = new Transaction(database, token)) {
            return tokenRepository.getToken(tx, tx.getUser(), id);
        }
    }

    @Override
    public List<Token> list(String token) {
        try (Transaction tx = new Transaction(database, token)) {
            return tokenRepository.findByUser(tx, tx.getUser());
        }
    }

    @Override
    public Token post(String token, Token tokenInfo) {
        try (Transaction tx = new Transaction(database, false, token)) {
            tokenRepository.insert(tx, new Token(tx.getUser(), tokenInfo.id, tokenInfo.createTime), SecretUtil.randomSecret());
            Token r = tokenRepository.get(tx, tokenInfo.user, tokenInfo.id);
            tx.commit();
            return r;
        }
    }

    @Override
    public Token delete(String token, String id) {
        try (Transaction tx = new Transaction(database, false, token)) {
            Token r = tokenRepository.get(tx, tx.getUser(), id);
            tokenRepository.delete(tx, tx.getUser(), id);
            tx.commit();
            return r;
        }
    }
}
