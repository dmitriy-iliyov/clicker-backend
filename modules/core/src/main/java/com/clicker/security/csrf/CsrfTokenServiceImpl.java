package com.clicker.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CsrfTokenServiceImpl implements CsrfTokenService {

    private final CsrfTokenRepository csrfTokenRepository;
    private final CsrfTokenMasker csrfTokenMasker;

    @Override
    public CsrfToken getMaskedToken(HttpServletRequest request) {
        CsrfToken csrfToken = csrfTokenRepository.loadToken(request);
        CsrfToken maskedCsrfToken = null;
        try {
            maskedCsrfToken = new DefaultCsrfToken(csrfToken.getHeaderName(),
                    csrfToken.getParameterName(), csrfTokenMasker.mask(csrfToken.getToken()));
        } catch (Exception e) {
            log.error("Error while getting csrf token : ", e);
        }
        return maskedCsrfToken;
    }
}
