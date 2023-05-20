package com.assistant.registration_service.user.model_data.enums;

public enum EmailMessage {
    EMAIL_CODE("Код підтвердження для мобільного додатка Emmo Techie");
    private final String first_message;

    EmailMessage(String first_message) {
        this.first_message = first_message;

    }

    public String getFirst_message() {
        return first_message;
    }
}
