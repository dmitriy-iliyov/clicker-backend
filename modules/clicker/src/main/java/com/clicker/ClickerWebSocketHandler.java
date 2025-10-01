package com.clicker;

import com.clicker.auth.TokenUserDetails;
import com.clicker.models.ClickerStateDto;
import com.clicker.security.WebSocketTokenFilter;
import com.clicker.service.ClickerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
@Slf4j
public class ClickerWebSocketHandler extends TextWebSocketHandler {

    private final ClickerService clickerServices;
    private final WebSocketTokenFilter webSocketTokenFilter;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        TokenUserDetails userDetails = webSocketTokenFilter.filter(session);
        session.sendMessage(new TextMessage(clickerServices.loadClickerData(userDetails.getUserId()).toJson()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        TokenUserDetails userDetails = webSocketTokenFilter.filter(session);
        session.sendMessage(new TextMessage(clickerServices.click(userDetails.getUserId()).toJson()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        TokenUserDetails userDetails = webSocketTokenFilter.filter(session);
        clickerServices.saveClickerData(userDetails.getUserId());
    }
}