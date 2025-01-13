CREATE TABLE async_request_multitasking (
    id BIGINT primary key GENERATED ALWAYS AS IDENTITY,
    context jsonb,
    status_async_request VARCHAR(30),
    scheduled_at timestamp with time zone,
    version INTEGER
);