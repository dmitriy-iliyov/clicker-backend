CREATE TABLE clicker_states(
    id UUID PRIMARY KEY,
    probability REAL NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);