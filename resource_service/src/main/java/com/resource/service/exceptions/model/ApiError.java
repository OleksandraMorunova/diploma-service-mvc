package com.resource.service.exceptions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class ApiError {
    private HttpStatus status;
    private String statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private List<String> errors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, String statusCode, String message, String error) {
        this();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public ApiError(HttpStatus status, String statusCode, String message, List<String> errors) {
        this();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String statusCode, String message, Throwable ex) {
        this();
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
    }
}
