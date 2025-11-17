CREATE TABLE user_authority(
    user_id UUID NOT NULL,
    authority_id UUID NOT NULL,
    PRIMARY KEY (user_id, authority_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (authority_id) REFERENCES authorities(id)
);

CREATE INDEX idx_user_id ON user_authority(user_id);
CREATE INDEX idx_authority_id ON user_authority(authority_id);