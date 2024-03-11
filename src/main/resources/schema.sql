BEGIN TRANSACTION;

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(63) UNIQUE NOT NULL,
    password VARCHAR(68) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(15) NOT NULL,
    authority VARCHAR(15) NOT NULL,
    PRIMARY KEY (username, authority),
    FOREIGN KEY (username) REFERENCES users (username)
);

COMMIT;