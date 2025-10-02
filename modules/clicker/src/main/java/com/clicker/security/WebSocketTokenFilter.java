package com.clicker.security;

import com.clicker.auth.TokenUserDetails;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface WebSocketTokenFilter {
    TokenUserDetails filter(WebSocketSession session) throws IOException;
}
