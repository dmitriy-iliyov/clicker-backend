package com.example.clicker.messaging.recovery;

import com.example.clicker.general.exceptions.models.recovery.RecoveryException;
import com.example.clicker.messaging.MessageDto;
import com.example.clicker.messaging.MessageService;
import com.example.clicker.user.UserFacade;
import io.jsonwebtoken.lang.Strings;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


@Service
public class EmailPasswordRecoveryService implements PasswordRecoveryService {

    private static final String URL = "https://localhost:8443/password-recovery/recover?token=";
    private final MessageService messageService;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserFacade userFacade;


    public EmailPasswordRecoveryService(@Qualifier("emailMessageService") MessageService messageService,
                                        PasswordRecoveryRepository passwordRecoveryRepository,
                                        UserFacade userFacade) {
        this.messageService = messageService;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.userFacade = userFacade;
    }

    @Override
    public void sendRecoveryMessage(String recipientResource) {
        UUID token = UUID.randomUUID();
        String encodeToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        messageService.sendMessage(new MessageDto(recipientResource, "Password recovering.",
                URL + encodeToken));

        passwordRecoveryRepository.save(token, recipientResource);
    }

    @Override
    public void recoverPassword(String inputToken, String password) throws RecoveryException {
        String decodeInput = Strings.ascii(Base64.getUrlDecoder().decode(inputToken.getBytes()));
        UUID token = UUID.fromString(decodeInput);

        String email = passwordRecoveryRepository.findAndDeleteByToken(token).orElseThrow(
                () -> new EntityNotFoundException("Password recovery token not found."));

        userFacade.updatePassword(email, password);
    }
}
