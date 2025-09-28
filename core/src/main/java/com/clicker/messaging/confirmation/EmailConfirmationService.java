package com.clicker.messaging.confirmation;


import com.clicker.messaging.MessageDto;
import com.clicker.messaging.MessageService;
import com.example.clicker.user.UserFacade;
import io.jsonwebtoken.lang.Strings;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;


@Log4j2
@Service
public class EmailConfirmationService implements ConfirmationService {

    private static final String URL = "https://localhost:8443/confirm/email/?token=";
    private final MessageService messageService;
    private final UserFacade userFacade;
    private final ConfirmationRepository confirmationRepository;


    public EmailConfirmationService(@Qualifier("emailMessageService") MessageService messageService, UserFacade userFacade,
                                    ConfirmationRepository confirmationRepository) {
        this.messageService = messageService;
        this.userFacade = userFacade;
        this.confirmationRepository = confirmationRepository;
    }

    @Override
    public void sendConfirmationMessage(String recipientResource) {

        UUID token = UUID.randomUUID();
        String encodedToken = Base64.getUrlEncoder().encodeToString(token.toString().getBytes());

        MessageDto message = new MessageDto(recipientResource, "Email confirming.", URL + encodedToken);

        messageService.sendMessage(message);
        confirmationRepository.save(token, recipientResource);
    }

    @Override
    public void validateConfirmationToken(String inputToken) {

        String decodedToken = Strings.ascii(Base64.getUrlDecoder().decode(inputToken.getBytes()));
        UUID token = UUID.fromString(decodedToken);

        String email = confirmationRepository.findAndDeleteByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Confirmation token not found."));

        userFacade.confirmByEmail(email);
    }
}
