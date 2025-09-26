package com.example.clicker.clicker;

import com.example.clicker.security.core.models.token.models.TokenUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
*
*  web socket
*
*  spring webflux
*
*/

@Controller
@RequestMapping("/clicker")
@RequiredArgsConstructor
public class ClickerController {

    private final ClickerService clickerServices;

    @Deprecated(forRemoval = true)
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String clickerForm() {
        return "clicker";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> click(@AuthenticationPrincipal TokenUserDetails tokenUserDetails,
                                                     @RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        Map<String, Object> response = new HashMap<>();

        if(Objects.equals(message, "Button clicked")){
            response.put("probability", clickerServices.countProbability(tokenUserDetails.getUserId()));
            return ResponseEntity.ok(response);
        }

        response.put("error", "Unexpected message: " + message + " in request.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
