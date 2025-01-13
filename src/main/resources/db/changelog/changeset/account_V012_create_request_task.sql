CREATE TABLE request_task (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    task_of_type VARCHAR(30),
    async_request_multitasking_id BIGINT NOT NULL,
    task_status VARCHAR(30),
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,
    version INTEGER,
    CONSTRAINT fk_request FOREIGN KEY (async_request_multitasking_id) REFERENCES async_request_multitasking (id) ON DELETE CASCADE
);