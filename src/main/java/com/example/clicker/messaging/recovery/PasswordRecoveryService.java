package com.example.clicker.messaging.recovery;

import com.example.clicker.general.exceptions.models.recovery.RecoveryException;

public interface PasswordRecoveryService {

    void sendRecoveryMessage(String recipientResource);

    void recoverPassword(String token, String password) throws RecoveryException;
}
