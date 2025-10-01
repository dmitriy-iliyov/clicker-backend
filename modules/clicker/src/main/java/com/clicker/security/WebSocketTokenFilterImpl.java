package com.clicker.security;

import com.clicker.auth.TokenUserDetails;
import com.clicker.auth.TokenUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class WebSocketTokenFilterImpl implements WebSocketTokenFilter {

    private final TokenUserDetailsRepository detailsRepository;

    @Override
    public TokenUserDetails filter(WebSocketSession session) {
        TokenUserDetails userDetails = (TokenUserDetails) session.getAttributes().get("principal");
        if (detailsRepository.existsById(userDetails.getToken().getId())) {
            return userDetails;
        }
        return null;
    }
}
