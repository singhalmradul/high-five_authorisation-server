BEGIN TRANSACTION;
-- -----------------------------------------------------------------------------------------------------
INSERT INTO oidc_client (
    id,
    client_id,
    client_secret,
    client_name,
    client_authentication_methods,
    authorization_grant_types,
    redirect_uris,
    scopes,
    client_settings,
    token_settings
) VALUES (
    -- id
    'aacc0e83-3ad9-4e0d-b7b2-bbf185e9e6da',
    -- client_id
    'high-five',
    -- client_secret
    'none',
    -- client_name
    'high-five',
    -- client_authentication_methods
    'none',
    -- authorization_grant_types
    'authorization_code',
    -- redirect_uris
    'http://localhost:3000',
    -- scopes
    'openid,profile',
    -- client_settings
    '{
        "@class":"java.util.HashMap",
        "settings.client.require-proof-key":true,
        "settings.client.require-authorization-consent":true
    }',
    -- token_settings
    '{
        "@class":"java.util.HashMap",
        "settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000],
        "settings.token.access-token-format":{
            "@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"
        },
        "settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],
        "settings.token.reuse-refresh-tokens":true,
        "settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],
        "settings.token.device-code-time-to-live":["java.time.Duration",300.000000000],
        "settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"]
    }'
);
-- -----------------------------------------------------------------------------------------------------
INSERT INTO user_account_details (
    user_id,
    username,
    email
) VALUES (
    'f57f49b9-ca47-43ab-b310-68cc4c6cc836',
    'singhalmradul',
    'singhalmradul@gmail.com'
);

INSERT INTO user_account_details (
    user_id,
    username,
    email
) VALUES (
    'e02acbc4-2e43-4dfe-8637-467d6b3b1074',
    'hawksea',
    'hawksea@email.io'
);

INSERT INTO user_account_details (
    user_id,
    username,
    email
) VALUES (
    '1075de58-5fa4-439e-8da0-59cdc6927618',
    'saltsamuel',
    'saltsamuel@email.io'
);

INSERT INTO user_account_details (
    user_id,
    username,
    email
) VALUES (
    'ead598f0-7a55-4266-a5d5-40e56ec19b88',
    'markelharry',
    'markelharry@email.io'
);

INSERT INTO user_account_details (
    user_id,
    username,
    email
) VALUES (
    '2b147a04-64b1-4cc9-9522-596c3e459917',
    'adamsmorgan',
    'adamsmorgan@email.io'
);
-- -----------------------------------------------------------------------------------------------------
INSERT INTO user_authentication_details (
    user_id,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES (
    'f57f49b9-ca47-43ab-b310-68cc4c6cc836',
    '{bcrypt}$2a$10$0oQGe9mCwWaWwLEes.oYP.rCA9DP9VNChq9j7nE2h0hTaUZnWq9BG',
    TRUE,
    TRUE,
    TRUE,
    TRUE
);

INSERT INTO user_authentication_details (
    user_id,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES (
    'e02acbc4-2e43-4dfe-8637-467d6b3b1074',
    '{bcrypt}$2a$10$3ypI1XDt6y0gmOTqZHUeMOAZzLo5JNU5ZurS0r9/2GwXIf1qUucEq',
    TRUE,
    TRUE,
    TRUE,
    TRUE
);

INSERT INTO user_authentication_details (
    user_id,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES (
    '1075de58-5fa4-439e-8da0-59cdc6927618',
    '{bcrypt}$2a$10$neuGvtnUuL0dl6EV7JFuFOuMC3Xdb3YGA/frhp/rVoeOckNC..x2e',
    TRUE,
    TRUE,
    TRUE,
    TRUE
);

INSERT INTO user_authentication_details (
    user_id,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES (
    'ead598f0-7a55-4266-a5d5-40e56ec19b88',
    '{bcrypt}$2a$10$CX6Lj/7zkEeaLDz9xZTlTOgd8Kz2vYxjxoMSCKjpnv4HMUR/rCgau',
    TRUE,
    TRUE,
    TRUE,
    TRUE
);

INSERT INTO user_authentication_details (
    user_id,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled
) VALUES (
    '2b147a04-64b1-4cc9-9522-596c3e459917',
    '{bcrypt}$2a$10$8hxqrRHYwUAMkFhr.oAnLeELSdD5mIIihLhlkgh37k38gAeakRoSi',
    TRUE,
    TRUE,
    TRUE,
    TRUE
);
-- -----------------------------------------------------------------------------------------------------
INSERT INTO authority (
    id,
    role
) VALUES (
    'eb0d39c2-2576-42e3-a336-7ca8eede4e73',
    'ROLE_USER'
);
-- -----------------------------------------------------------------------------------------------------
INSERT INTO user_authority (
    user_id,
    authority_id
) VALUES (
    'f57f49b9-ca47-43ab-b310-68cc4c6cc836',
    'eb0d39c2-2576-42e3-a336-7ca8eede4e73'
);

INSERT INTO user_authority (
    user_id,
    authority_id
) VALUES (
    'e02acbc4-2e43-4dfe-8637-467d6b3b1074',
    'eb0d39c2-2576-42e3-a336-7ca8eede4e73'
);

INSERT INTO user_authority (
    user_id,
    authority_id
) VALUES (
    '1075de58-5fa4-439e-8da0-59cdc6927618',
    'eb0d39c2-2576-42e3-a336-7ca8eede4e73'
);

INSERT INTO user_authority (
    user_id,
    authority_id
) VALUES (
    'ead598f0-7a55-4266-a5d5-40e56ec19b88',
    'eb0d39c2-2576-42e3-a336-7ca8eede4e73'
);

INSERT INTO user_authority (
    user_id,
    authority_id
) VALUES (
    '2b147a04-64b1-4cc9-9522-596c3e459917',
    'eb0d39c2-2576-42e3-a336-7ca8eede4e73'
);
-- -----------------------------------------------------------------------------------------------------
COMMIT;