package com.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "測試")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "Server health checking", description = "")
    @GetMapping("/healthCheck")
    public ResponseEntity<?> healthCheck() {

        return ResponseEntity.ok("Server is running!");
    }

}
