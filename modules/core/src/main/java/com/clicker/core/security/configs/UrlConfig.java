package com.clicker.core.security.configs;

public final class UrlConfig {
    public static final String USER_REGISTRATION_URL = "/api/users";
    public static final String USER_LOGIN_URL = "/api/login";
    public static final String GET_CSRF_TOKEN_URL = "/api/csrf";
    public static final String CONFIRMATION_URL = "/api/confirmation/**";
    public static final String CONFIRMATION_PAGE_URL = "/ui/confirmation";
    public static final String PASSWORD_RECOVERING_URL = "/api/password-recovery/**";
    public static final String USER_LOGOUT_URL = "/api/logout";

    public static final String HOME_PAGE_URL = "/ui/home";
    public static final String USER_REGISTRATION_PAGE_URL = "/ui/users/create";
    public static final String USER_LOGIN_PAGE_URL = "/ui/users/login";
    public static final String CLICKER_PAGE_URL = "/ui/clicker";
    public static final String ADMIN_HOME_PAGE_URL = "/ui/admins/home";
    public static final String PROFILE_PAGE_URL = "/ui/users/user/profile";

    public static final String [] NO_AUTH_PERMITTED_URL_LIST = {
            HOME_PAGE_URL,
            USER_REGISTRATION_URL, USER_REGISTRATION_PAGE_URL,
            USER_LOGIN_URL, USER_LOGIN_PAGE_URL,
            CONFIRMATION_URL, CONFIRMATION_PAGE_URL,
            PASSWORD_RECOVERING_URL, "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "user_pass_recovery_form.html",
            "/test/**", "/currencies/**", HOME_PAGE_URL
    };
    public static final String [] NO_CSRF_PERMITTED_URL_LIST = {
            GET_CSRF_TOKEN_URL,
            USER_REGISTRATION_URL, USER_REGISTRATION_PAGE_URL,
            USER_LOGIN_URL, USER_LOGIN_PAGE_URL,
            CONFIRMATION_URL, CONFIRMATION_PAGE_URL,
            PASSWORD_RECOVERING_URL, "/test/**",  "/api/admin/currencies/**"
    };
}
