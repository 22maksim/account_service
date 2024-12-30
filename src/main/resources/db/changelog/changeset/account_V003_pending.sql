CREATE TABLE IF NOT EXISTS pending (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    amount NUMERIC(13, 2) NOT NULL,
    account_id VARCHAR(20) NOT NULL,
    number_transaction BIGINT NOT NULL,
    type_payment_request VARCHAR(15) NOT NULL,
    payment_status VARCHAR(15) NOT NULL,
    currency VARCHAR(10),
    message VARCHAR(300)
);