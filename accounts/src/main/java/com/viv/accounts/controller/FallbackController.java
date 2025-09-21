package com.viv.accounts.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/service")
    public ResponseEntity<String> serviceFallback() {
        return ResponseEntity.status(503).body("Service temporarily unavailable - fallback");
    }

}
