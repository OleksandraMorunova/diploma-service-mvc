package com.assistant.registration_service.user.model_data.enums;

public enum UserStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    DEACTIVATED("DEACTIVATED"),
    BLOCKED("BLOCKED"),
    SPAM("SPAM"),
    DELETED("DELETED");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
