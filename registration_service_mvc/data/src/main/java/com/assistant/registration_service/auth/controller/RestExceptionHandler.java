package com.assistant.registration_service.auth.controller;

import com.assistant.registration_service.auth.domain.ApiError;
import com.assistant.registration_service.auth.exceptions.EntityNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override //Виникає, коли надсилається запит за допомогою непідтримуваного методу HTTP.
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, String.valueOf(HttpStatus.BAD_REQUEST.value()), error, ex));
    }

    @Override //Виняток виникає, коли аргумент, анотований @Valid, не пройшов перевірку.
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override //Виняток виникає, коли в запиті відсутній параметр.
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError =  new ApiError(HttpStatus.BAD_REQUEST,  String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ ConstraintViolationException.class }) //Виняток повідомляє про результат порушення обмежень.
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,  String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getLocalizedMessage(), errors);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class }) //Виняток виникає, коли аргумент методу не є очікуваним типом.
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, String.valueOf(HttpStatus.BAD_REQUEST.value()), ex.getLocalizedMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ EntityNotFoundException.class }) //Виняток виникає коли не знайдено дані.
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        String error =  "Data which client search don't exist";
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getLocalizedMessage(), error);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ AuthException.class })
    protected ResponseEntity<Object> handleAuthException(AuthException ex){
        String error =  "Token don't right type or the payload";
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, String.valueOf(HttpStatus.NOT_FOUND.value()), ex.getMessage(), error);
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ ExpiredJwtException.class })
    protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex){
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, String.valueOf(HttpStatus.FORBIDDEN.value()), ex.getMessage(), "JWT expired and it rejected");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ MalformedJwtException.class })
    protected ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex){
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, String.valueOf(HttpStatus.FORBIDDEN.value()), ex.getMessage(), "Malformed jwt exception");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler({ SecurityException.class })
    protected ResponseEntity<Object> handleSecurityException(SecurityException ex){
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, String.valueOf(HttpStatus.FORBIDDEN.value()), ex.getMessage(), "Calculating a signature or verifying an existing signature of a JWT failed.");
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
