package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.serviceresponse.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/register")
@Tag(name = "Register-Controller", description = "Registration of a new user")
public class RegisterController {

    @Autowired
    private UserService userService;

    //register new user
    @PostMapping("/user")
    @Operation(summary = "Create new user", description = "Returns the user details ")
    public ResponseEntity<ServiceResponse<User>> createNewUser(@RequestBody @Valid User user) {
        var register = userService.createNewUser(user);
        return new ResponseEntity<>(register, HttpStatus.OK);
    }
}
