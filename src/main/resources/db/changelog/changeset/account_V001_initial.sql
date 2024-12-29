
/* FUNCTIONS */

CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.update_at = current_timestamp;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

/* TABLES */

CREATE TABLE IF NOT EXISTS account (
    id VARCHAR(20) PRIMARY KEY,
    owner_id bigint,
    balance_id BIGINT,
    type VARCHAR(15) NOT NULL,
    currency VARCHAR(15) NOT NULL,
    status VARCHAR(15) NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp,
    close_at TIMESTAMP,
    version INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS owners (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        owner_id BIGINT NOT NULL,
                        type VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS balance (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_id varchar(20),
    authorization_balance bigint default 200,
    current_balance bigint default 0,
    created_at timestamptz DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp,
    version INT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS balance_audit
(
    id                   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_id           varchar(20),
    type                 varchar(15) not null,
    authorization_amount int       default 200,
    actual_amount        int       default 0,
    transaction_changed  bigint   not null,
    created_at           TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE IF NOT EXISTS free_account_numbers
(
    id             bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    account_number VARCHAR(20),
    type           VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS account_numbers_sequence
(
    type    VARCHAR(15) PRIMARY KEY,
    counter BIGINT default 0,
    version INTEGER default 0
);

CREATE TABLE IF NOT EXISTS savings_account
(
    account_id     VARCHAR(20) PRIMARY KEY,
    tariff_history json,
    update_percent TIMESTAMP,
    created_at     TIMESTAMP DEFAULT current_timestamp,
    update_at      TIMESTAMP DEFAULT current_timestamp,
    version        int       DEFAULT 1
);

CREATE TABLE IF NOT EXISTS rate
(
    id          bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tariff_rate VARCHAR(15),
    type        VARCHAR(15),
    history     json
);

-- INSERT  INTO account_numbers_sequence  (type, counter)
-- VALUES ('DEBIT', 0),
--        ('CREDIT', 0),
--        ('CUMULATIVE', 0);

/* dependencies */
-- ALTER TABLE account
--     ADD CONSTRAINT fk_owner
--         foreign key (owner_id)
--             REFERENCES owners(id);
--
-- ALTER TABLE balance
--     ADD CONSTRAINT fk_balance_account
--         FOREIGN KEY (account_id)
--             REFERENCES account (id);
--
-- ALTER TABLE balance_audit
--     ADD CONSTRAINT fk_balance_audit_account
--         FOREIGN KEY (account_id)
--             REFERENCES account (id);

/* INDEXES */

-- CREATE INDEX idx_owner ON account (owner_id);