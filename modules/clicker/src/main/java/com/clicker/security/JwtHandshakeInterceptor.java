package com.clicker.security;

import com.clicker.auth.CookieJwtAuthenticationConverter;
import com.clicker.auth.TokenUserDetails;
import com.clicker.auth.TokenUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
public class JwtHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final CookieJwtAuthenticationConverter converter;
    private final TokenUserDetailsService detailsService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
            HttpServletRequest httpServletRequest = servletServerHttpRequest.getServletRequest();
            PreAuthenticatedAuthenticationToken authentication = (PreAuthenticatedAuthenticationToken) converter.convert(httpServletRequest);
            TokenUserDetails userDetails = (TokenUserDetails) detailsService.loadUserDetails(authentication);
            attributes.put("principal", userDetails);
            return true;
        } else {
            return false;
        }
    }
}
