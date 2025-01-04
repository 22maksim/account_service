CREATE TABLE IF NOT EXISTS cumulative_tariff (
    type VARCHAR(20) PRIMARY KEY,
    history JSON,
    update_at TIMESTAMP DEFAULT current_timestamp
);

ALTER TABLE account ADD COLUMN tariff_type VARCHAR(20);

CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
   NEW.update_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_update_at
    BEFORE UPDATE ON cumulative_tariff
    FOR EACH ROW
    EXECUTE FUNCTION update_timestamp();