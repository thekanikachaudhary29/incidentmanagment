package com.demo.service;

import com.demo.dto.ForgotPasswordRequest;
import com.demo.dto.LocationOffice;
import com.demo.dto.LocationResponse;
import com.demo.dto.LoginDto;
import com.demo.entity.User;
import com.demo.repository.ForgotPasswordRepository;
import com.demo.repository.IncidentRepository;
import com.demo.repository.UserRepository;
import com.demo.serviceresponse.ServiceResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncidentRepository incidentRepository;
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    private LocationService locationService;

    public String findByEmail(@Valid LoginDto loginUser) {
        Optional<String> email = userRepository.findLogInAndPassword(loginUser.getEmail());
        if (email.isPresent()) {
            return "Login Successful !! ";
        } else {
            return "Invalid Credentials";
        }
    }

    public ServiceResponse<User> createNewUser(User user) {
        LocationResponse pin = locationService.getCityAndCountry(user.getPinCode());
        User email = userRepository.findByEmail(user.getEmail());
        if (email != null) {
            throw new RuntimeException("Email already exists");
        }
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setAddress(user.getAddress());
        user.setCountry(user.getCountry());
        user.setState(user.getState());
        user.setRegion(user.getRegion());
        if (pin.getLocationOffice() != null && !pin.getLocationOffice().isEmpty()) {
            LocationOffice office = pin.getLocationOffice().get(0);  // get first post office
            user.setRegion(office.getRegion());
            user.setState(office.getState());
            user.setCountry(office.getCountry());
        } else {
            throw new RuntimeException("Invalid or unsupported PIN code");
        }
        user.setPinCode(user.getPinCode());
        if (!user.getMobileNumber().matches("\\d{10}")) {
            throw new RuntimeException("Mobile Number digits must be 10 digits");
        }
        user.setMobileNumber(user.getMobileNumber());
        user.setFax(user.getFax());
        user.setPhone(user.getPhone());
        user.setPassword(user.getPassword());
        var newUser = userRepository.save(user);
        ServiceResponse<User> response = new ServiceResponse<>();
        response.setMessage("New User Registered !!!");
        response.setData(newUser);
        return response;
    }

    public String resetPassword(@Valid ForgotPasswordRequest request) {
        Optional<ForgotPasswordRequest> email = forgotPasswordRepository.findByEmailAndSecurityQuestionAndSecurityAnswer(request.getSecurityQuestion(), request.getSecurityAnswer(), request.getEmail());
        if (email.isPresent()) {
            ForgotPasswordRequest reset = email.get();
            if (reset.getSecurityQuestion().equalsIgnoreCase(request.getSecurityQuestion())) {
                return "Password reset succesfully !!";
            } else {
                return "Incorrect Security Answer";
            }
        }
        return "User Not Found";
    }
}