package com.clicker;

import com.clicker.service.ClickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequiredArgsConstructor
public class ClickerWebSocketHandler extends TextWebSocketHandler {

    private final ClickerService clickerServices;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        clickerServices.loadClickerData();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        clickerServices.saveClickerData();
    }
}


//@PreAuthorize("hasAuthority('ROLE_USER')")
//public ResponseEntity<Map<String, Object>> click(@AuthenticationPrincipal TokenUserDetails tokenUserDetails) {
//    clickerServices.countProbability(tokenUserDetails.getUserId());
//    return ResponseEntity
//            .status(HttpStatus.NO_CONTENT)
//            .build();
//}