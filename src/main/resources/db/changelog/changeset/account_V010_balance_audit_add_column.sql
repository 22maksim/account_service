ALTER TABLE balance_audit ADD COLUMN operation_type_percent_id BIGINT default null;
ALTER TABLE balance_audit ADD COLUMN merchant_percent_id BIGINT default null;
ALTER TABLE balance_audit ADD COLUMN amount_operation BIGINT;
ALTER TABLE balance_audit ADD COLUMN type_transactional_balance VARCHAR(20);