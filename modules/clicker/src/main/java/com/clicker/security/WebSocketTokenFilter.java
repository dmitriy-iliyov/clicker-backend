package com.clicker.security;

import com.clicker.auth.TokenUserDetails;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketTokenFilter {
    TokenUserDetails filter(WebSocketSession session);
}
