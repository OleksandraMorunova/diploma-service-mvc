package com.assistant.registration_service.user.model_data.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"), USER("USER");

    private final String roleDecoded;

    Role(String vale) {
        this.roleDecoded = vale;
    }

    @Override
    public String getAuthority() {
        return roleDecoded;
    }
}
