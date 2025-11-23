package com.clicker.core.messaging.confirmation;

import com.clicker.core.domain.user.models.dto.EmailConfirmationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EmailConfirmationListener {

    private final ConfirmationService service;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listen(EmailConfirmationEvent event) {
        service.sendConfirmationMessage(event.email());
    }
}
