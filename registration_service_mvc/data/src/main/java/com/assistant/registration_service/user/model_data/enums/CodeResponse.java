package com.assistant.registration_service.user.model_data.enums;

public enum CodeResponse {
    SUCCESS_SENT_CODE_TO_PHONE_NUMBER("The code send to the phone number."),
    FAILURE_SENT_CODE_TO_PHONE_NUMBER("Failed to send code to the phone number."),
    SUCCESS_SENT_CODE_TO_EMAIL("The code send to the email."),
    FAILURE_SENT_CODE_TO_EMAIL("Failed to send code to the email."),
    FOUND_CODE("The code founded."),
    NO_FOUND_CODE("The code didn't found."),
    FOUND_PHONE_NUMBER("The phone number founded."),
    N0_FOUND_PHONE_NUMBER("The phone number didn't found."),
    FOUND_EMAIL("The email founded."),
    NO_FOUND_EMAIL("The email didn't found."),
    ALLOWED("Access is allowed to you."),
    DENIED("Access denied to you."),
    UPDATE_USER_DETAILS("User details updated."),
    NO_UPDATE_USER_DETAILS("User details didn't update."),
    CREATED_NEW_USER("New user created."),
    NO_CREATED_NEW_USER("New user didn't creat.");
    private final String message;

    CodeResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
