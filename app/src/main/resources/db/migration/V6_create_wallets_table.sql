CREATE SEQUENCE wallets_seq
    START 1 INCREMENT 1 MINVALUE 1 NO MAXVALUE CACHE 20;

CREATE TABLE wallets(
    id BIGINT PRIMARY KEY,
    user_id UUID NOT NULL,
    currency_id BIGINT NOT NULL,
    address VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (currency_id) REFERENCES currencies(id)
);