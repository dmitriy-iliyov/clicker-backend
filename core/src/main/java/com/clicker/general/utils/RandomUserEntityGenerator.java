package com.clicker.general.utils;

import com.example.clicker.security.core.models.authority.models.AuthorityEntity;
import com.example.clicker.user.models.entity.UserEntity;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@UtilityClass
public class RandomUserEntityGenerator {

    private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "outlook.com", "mail.com", "example.com"};
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz1234567890";
    private static final Random random = new Random();


    private static String generateRandomEmail() {
        StringBuilder localPart = new StringBuilder();

        int length = random.nextInt(6) + 5;
        for (int i = 0; i < length; i++) {
            localPart.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        String domain = DOMAINS[random.nextInt(DOMAINS.length)];

        return localPart + "@" + domain;
    }

    private String generateRandomPassword() {
        StringBuilder sb = new StringBuilder();
        int length = random.nextInt(10) + 10;
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public UserEntity generate(AuthorityEntity authorityEntity){
        return new UserEntity(
                UuidCreator.getTimeOrderedEpoch(),
                generateRandomEmail(),
                generateRandomPassword(),
                authorityEntity
        );
    }

    public UserEntity generate(PasswordEncoder passwordEncoder, AuthorityEntity authorityEntity){
        return new UserEntity(
                UuidCreator.getTimeOrderedEpoch(),
                generateRandomEmail(),
                passwordEncoder.encode(generateRandomPassword()),
                authorityEntity
        );
    }
}
