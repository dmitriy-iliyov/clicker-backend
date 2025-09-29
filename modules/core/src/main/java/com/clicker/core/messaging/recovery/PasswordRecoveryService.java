package com.clicker.core.messaging.recovery;

import com.clicker.core.exception.RecoveryException;

public interface PasswordRecoveryService {

    void sendRecoveryMessage(String recipientResource);

    void recoverPassword(String token, String password) throws RecoveryException;
}
