package com.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/authentication")
    public ResponseEntity<?> goTestAuthentication() {
        return new ResponseEntity<>("isAuthentication", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/authorization")
    public ResponseEntity<?> goTestAuthorization() {
        return new ResponseEntity<>("isAuthorization", HttpStatus.OK);
    }
}
