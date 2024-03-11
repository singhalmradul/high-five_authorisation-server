-- INSERT INTO client (
--     id,
--     client_id,
--     client_secret,
--     client_name,
--     client_authentication_methods,
--     authorization_grant_types,
--     redirect_uris,
--     scopes,
--     client_settings,
--     token_settings
-- ) VALUES (
--     'high-five',
--     'high-five',
--     'none',
--     'high-five',
--     'none',
--     'authorization_code',
--     'http://localhost:3000',
--     'openid,profile',
--     '{"require-authorization-consent": true}',
--     '{"require-proof-key": true}'
-- );

BEGIN TRANSACTION;

INSERT INTO users (
    username,
    password,
    enabled
) VALUES (
    'user',
    '{noop}password',
    true
), (
    'hawksea',
    '{bcrypt}$2a$10$3ypI1XDt6y0gmOTqZHUeMOAZzLo5JNU5ZurS0r9/2GwXIf1qUucEq',
    true
), (
    'saltsamuel',
    '{bcrypt}$2y$10$naD37Mw1bMr94TcOdlRusuLLY.Ke96lnWCkMbOZpagtWo3UPJk5WO',
    true
), (
    'markelharry',
    '{bcrypt}$2a$10$PIfJo8tkpzbSyrCrVS8cKO9rUOdm4FUNBL1qQD2onNC5abWX75V9.',
    true
), (
    'adamsmorgan',
    '{bcrypt}$2a$10$8hxqrRHYwUAMkFhr.oAnLeELSdD5mIIihLhlkgh37k38gAeakRoSi',
    true
);

INSERT INTO authorities (
    username,
    authority
) VALUES (
    'user',
    'ROLE_USER'
), (
    'hawksea',
    'ROLE_USER'
), (
    'saltsamuel',
    'ROLE_USER'
), (
    'markelharry',
    'ROLE_USER'
), (
    'adamsmorgan',
    'ROLE_USER'
);

COMMIT;
