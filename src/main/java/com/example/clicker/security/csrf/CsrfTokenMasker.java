package com.example.clicker.security.csrf;

public interface CsrfTokenMasker {

    String mask(String csrfToken) throws Exception;

}
