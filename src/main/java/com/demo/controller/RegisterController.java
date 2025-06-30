package com.demo.controller;

import com.demo.dto.LocationResponse;
import com.demo.entity.User;
import com.demo.service.LocationService;
import com.demo.service.UserService;
import com.demo.serviceresponse.ServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@Tag(name = "Register-Controller", description = "Registration for a new user")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    //register new user
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new user", description = "Returns the user details ")
    public ResponseEntity<ServiceResponse<User>> createNewUser(@RequestBody @Valid User user) {
        var register = userService.createNewUser(user);
        return new ResponseEntity<>(register, HttpStatus.OK);
    }

    //get city and country name by entering pin code
    @GetMapping("/pincode")
    @Operation(summary = "Get city and country name by entering pin code", description = "Returns the user details ")
    public ResponseEntity<LocationResponse> getCityAndCountry(String pincode) {
        LocationResponse location = locationService.getCityAndCountry(pincode);
        return ResponseEntity.ok(location);
    }
}