CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE OR REPLACE FUNCTION update_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.update_at = current_timestamp;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_update_at
    BEFORE UPDATE ON balance_audit
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_update_at
    BEFORE UPDATE ON account
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TRIGGER set_update_at
    BEFORE UPDATE ON savings_account
    FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

CREATE TABLE account (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    owner_id bigint,
    type smallint NOT NULL,
    currency smallint NOT NULL,
    status smallint NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp,
    close_at TIMESTAMP,
    version INT DEFAULT 1
);

CREATE TABLE balance (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    account_id varchar(36),
    authorization_balance bigint default 200,
    current_balance bigint default 0,
    created_at timestamptz DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp,
    version INT DEFAULT 1
);

CREATE TABLE balance_audit (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    account_id varchar(36),
    type smallint not null,
    authorization_amount int default 200,
    actual_amount int default 0,
    transaction_changed bigint not null,
    created_at TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE free_account_numbers (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    account_number VARCHAR(20),
    type smallint
);

CREATE TABLE savings_account (
    account_id VARCHAR(36) PRIMARY KEY,
    tariff_history json,
    update_percent TIMESTAMP,
    created_at TIMESTAMP DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp,
    version int DEFAULT 1
);

CREATE TABLE rate (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    tariff_rate VARCHAR(5),
    type smallint,
    history json
);
