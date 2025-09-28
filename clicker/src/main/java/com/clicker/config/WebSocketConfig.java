package com.clicker.config;

import com.clicker.service.ClickerService;
import com.clicker.ClickerWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ClickerService clickerService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ClickerWebSocketHandler(clickerService), "/clicker")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
