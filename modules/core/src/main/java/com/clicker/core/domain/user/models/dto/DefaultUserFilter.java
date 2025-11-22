package com.clicker.core.domain.user.models.dto;

public class DefaultUserFilter extends AbstractUserFilter {
    protected final String username;

    public DefaultUserFilter(int page, int size, String username) {
        super(page, size);
        this.username = username;
    }
}
