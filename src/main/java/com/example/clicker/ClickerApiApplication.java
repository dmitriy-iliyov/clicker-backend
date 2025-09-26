package com.example.clicker;

import com.example.clicker.currency.CurrencyRepository;
import com.example.clicker.security.core.models.authority.AuthorityRepository;
import com.example.clicker.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAsync
public class ClickerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickerApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner testData(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, CurrencyRepository currencyRepository) {
            return args -> {
//                List<UserEntity> users = new ArrayList<>(100);
//                AuthorityEntity authorityEntity =  authorityRepository.findByAuthority(Authority.ROLE_USER)
//                        .orElseThrow(AuthorityNotFoundException::new);
//                AuthorityEntity authorityEntity2 =  authorityRepository.findByAuthority(Authority.ROLE_UNCONFIRMED_USER)
//                        .orElseThrow(AuthorityNotFoundException::new);
//                users.addAll( List.of(
//                        new UserEntity(
//                                UuidCreator.getTimeOrderedEpoch(),
//                                "1@gmail.com",
//                                passwordEncoder.encode("password12"),
//                                authorityEntity
//                        ),
//                        new UserEntity(
//                                UuidCreator.getTimeOrderedEpoch(),
//                                "admin@gmail.com",
//                                passwordEncoder.encode("adminPassword"),
//                                authorityRepository.findByAuthority(Authority.ROLE_ADMIN)
//                                        .orElseThrow(AuthorityNotFoundException::new)
//                        ),
//                        new UserEntity(
//                                UuidCreator.getTimeOrderedEpoch(),
//                                "eliv@gmail.com",
//                                passwordEncoder.encode("evilpassword"),
//                                authorityEntity
//                        )
//                ));
//                for (int i = 0; i < 100; i++) {
//                    users.add(RandomUserEntityGenerator.generate(passwordEncoder, authorityEntity));
//                }
//                userRepository.saveAll(users);
//                currencyRepository.saveAll(
//                        List.of(
//                                new CurrencyEntity("BTC"),
//                                new CurrencyEntity("SLN"),
//                                new CurrencyEntity("TOR")
//                        )
//                );
            };
    }
}
