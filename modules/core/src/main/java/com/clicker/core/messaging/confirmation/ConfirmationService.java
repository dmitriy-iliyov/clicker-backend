package com.clicker.core.messaging.confirmation;

public interface ConfirmationService {

    void sendConfirmationMessage(String recipientResource);

    void validateConfirmationToken(String inputToken);

}
