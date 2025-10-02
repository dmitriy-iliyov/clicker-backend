package com.clicker.security;

import com.clicker.auth.TokenUserDetails;
import com.clicker.auth.TokenUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WebSocketTokenFilterImpl implements WebSocketTokenFilter {

    private final TokenUserDetailsRepository detailsRepository;

    @Override
    public TokenUserDetails filter(WebSocketSession session) throws IOException {
        TokenUserDetails userDetails = (TokenUserDetails) session.getAttributes().get("principal");
        if (detailsRepository.existsById(userDetails.getToken().getId())) {
            return userDetails;
        }
        session.close(CloseStatus.POLICY_VIOLATION);
        return null;
    }
}
