package com.clicker.core.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;

public interface CsrfTokenService {
    CsrfToken getMaskedToken(HttpServletRequest request);
}
