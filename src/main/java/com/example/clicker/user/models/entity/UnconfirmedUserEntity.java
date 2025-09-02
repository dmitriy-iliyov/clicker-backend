package com.example.clicker.user.models.entity;

import com.example.clicker.general.utils.uuid.UuidFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Data
@RedisHash(value = "user:uncf", timeToLive = 86400)
public class UnconfirmedUserEntity {

    @Id
    private String email;
    private UUID id;
    private String password;
    @Indexed
    private String username;


    public UnconfirmedUserEntity() {
        this.id = UuidFactory.generate();
    }

    @JsonCreator
    public UnconfirmedUserEntity(@JsonProperty("id") UUID id,
                                 @JsonProperty("email") String email,
                                 @JsonProperty("password") String password,
                                 @JsonProperty("username") String username
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }
}
