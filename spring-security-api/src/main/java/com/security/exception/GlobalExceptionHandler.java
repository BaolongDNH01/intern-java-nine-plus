package com.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException authenticationException) {
        System.err.println(authenticationException.getMessage());
        return ResponseEntity.status(401).body(authenticationException.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        System.err.println(accessDeniedException.getMessage());
        return ResponseEntity.status(403).body(accessDeniedException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnwantedException(Exception e) {
        System.err.println(e.getMessage());
        return ResponseEntity.status(500).body("Unknown error");
    }
}
