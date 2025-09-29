package com.clicker.core.messaging.recovery;


import com.clicker.core.exception.RecoveryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
        name = "Password recovery",
        description = "Method to password recovery"
)
@RestController
@RequestMapping("/password-recovery")
@RequiredArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @Operation(summary = "Send recovery link to user resource")
    @PostMapping("/request")
    public ResponseEntity<?> requestToPasswordRecovery(@Parameter(description = "user resource")
                                                         @RequestParam("resource") String recourse) {
        passwordRecoveryService.sendRecoveryMessage(recourse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Reset password by token")
    @PatchMapping("/recover")
    public ResponseEntity<?> setNewPassword(@Parameter(description = "verifying token")
                                                @RequestParam("token") String token,
                                            @Parameter(description = "password")
                                                @RequestBody Map<String, String> payload) throws RecoveryException {
        passwordRecoveryService.recoverPassword(token, payload.get("password"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
