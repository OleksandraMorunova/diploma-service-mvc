package com.resource.service.model;

public enum CheckTask {
    NOT_REVIEWED("NOT_REVIEWED"),
    REVIEWED("REVIEWED"),
    RETURNED("RETURNED");

    private final String checkLine;

    CheckTask(String checkLine) {
        this.checkLine = checkLine;
    }

    public String getCheckLine() {
        return checkLine;
    }
}
