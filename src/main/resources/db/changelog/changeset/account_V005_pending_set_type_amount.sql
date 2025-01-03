ALTER TABLE pending DROP COLUMN amount;

ALTER TABLE pending ADD COLUMN amount BIGINT default 0;