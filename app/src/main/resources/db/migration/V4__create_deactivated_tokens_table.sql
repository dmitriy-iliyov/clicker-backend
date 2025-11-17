CREATE TABLE deactivated_tokens(
    id UUID PRIMARY KEY,
    deactivate_at TIMESTAMP NOT NULL
);