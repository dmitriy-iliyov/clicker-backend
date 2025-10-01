package com.clicker.core.security.core.handlers.logout;

import com.clicker.auth.TokenUserDetails;
import com.clicker.core.security.core.models.token.DeactivateTokenService;
import com.clicker.core.security.core.models.token.models.TokenEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.time.Instant;

@RequiredArgsConstructor
public class DeactivatingJwtLogoutHandler implements LogoutHandler {

    private final DeactivateTokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication != null && authentication.getPrincipal() instanceof TokenUserDetails tokenUserDetails) {
            tokenService.deactivate(new TokenEntity(tokenUserDetails.getToken().getId(), Instant.now()));
        }
    }
}
