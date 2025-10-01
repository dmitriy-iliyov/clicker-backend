package com.clicker.config;

import com.clicker.ClickerWebSocketHandler;
import com.clicker.auth.CookieJwtAuthenticationConverter;
import com.clicker.auth.TokenDeserializer;
import com.clicker.auth.TokenUserDetailsService;
import com.clicker.security.JwtHandshakeInterceptor;
import com.clicker.security.WebSocketTokenFilter;
import com.clicker.service.ClickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    @Value("${clicker.ui.origin}")
    private String UI_ORIGIN;
    private final ClickerService clickerService;
    private final WebSocketTokenFilter webSocketTokenFilter;
    private final TokenDeserializer tokenDeserializer;
    private final TokenUserDetailsService detailsService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ClickerWebSocketHandler(clickerService, webSocketTokenFilter), "/api/clicker")
                .setAllowedOrigins(UI_ORIGIN)
                .addInterceptors(
                        new JwtHandshakeInterceptor(new CookieJwtAuthenticationConverter(tokenDeserializer), detailsService)
                );
    }
}
