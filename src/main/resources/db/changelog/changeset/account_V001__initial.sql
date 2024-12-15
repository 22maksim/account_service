CREATE TABLE account (
    id UUID DEFAULT uuid_generate_v4(),
    owner_id bigint,
    type smallint NOT NULL,
    currency smallint NOT NULL,
    status smallint NOT NULL,
    created_at TIMESTAMP DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
    close_at TIMESTAMP,
    version INT NOT NULL
);

CREATE TABLE balance (
    id UUID DEFAULT uuid_generate_v4(),
    account int,
    authorization_balance bigint default 0,
    current_balance bigint default 0,
    created_at timestamptz DEFAULT current_timestamp,
    update_at TIMESTAMP DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
    version INT NOT NULL
);

