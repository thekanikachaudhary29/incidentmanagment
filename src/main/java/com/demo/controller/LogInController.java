package com.demo.controller;

import com.demo.dto.ForgotPasswordRequest;
import com.demo.dto.LoginDto;
import com.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Tag(name= "LogIn-Controller", description = "API for managing Log In details")
public class LogInController {

    @Autowired
    private UserService userService;

    //login new user
    @PostMapping("/user")
    @Operation(summary = "LogIn new user for creating incident", description = "Log in user details")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginUser) {
        userService.findByEmail(loginUser);
        return ResponseEntity.ok("LogIn Successfully !!");
    }

    //forgot password
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ForgotPasswordRequest request) {
       userService.resetPassword(request);
       return ResponseEntity.ok("Password has been reset");
    }
}
