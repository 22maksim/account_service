CREATE TABLE request (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    payment_amount bigint,
    account_number VARCHAR(20),
    type_request VARCHAR(15),
)