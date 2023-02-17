package com.assistant.api.user.data.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"), USER("USER");

    private final String vale;

    Role(String vale) {
        this.vale = vale;
    }

    @Override
    public String getAuthority() {
        return vale;
    }
}
