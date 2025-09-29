package com.clicker.core.messaging.confirmation;

import java.util.Optional;
import java.util.UUID;


public interface ConfirmationRepository {

    void save(UUID token, String confResource);

    Optional<String> findAndDeleteByToken(UUID token);
}
