package com.clicker.core.security.csrf;

public interface CsrfTokenMasker {

    String mask(String csrfToken) throws Exception;

}
