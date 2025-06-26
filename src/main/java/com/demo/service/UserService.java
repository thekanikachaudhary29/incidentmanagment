package com.demo.service;

import com.demo.dto.ForgotPassword;
import com.demo.dto.LoginDto;
import com.demo.entity.User;
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

    public String findByEmail(@Valid LoginDto loginUser) {
        Optional<User> email = userRepository.findLogInAndPassword(loginUser.getEmail());
        if(email.isPresent()) {
            return "Login Successful !! ";
        } else {
            return "Invalid Credentials";
        }
    }

    public ServiceResponse<User> createNewUser(User user) {
      String email = userRepository.findByEmail(user.getEmail());
        if(user.getEmail().equals(email)) {
            throw new RuntimeException("Email already exists");
        }
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        user.setAddress(user.getAddress());
        user.setCountry(user.getCountry());
        user.setState(user.getState());
        user.setCity(user.getCity());
        user.setPinCode(user.getPinCode());
        user.setMobileNumber(user.getMobileNumber());
        user.setFax(user.getFax());
        user.setPhone(user.getPhone());
        user.setPassword(user.getPassword());
        ServiceResponse<User> response = new ServiceResponse<>();
        response.setMessage("New User Registered !!!");
        response.setData(user);
        return response;
    }

    public String resetPassword(@Valid ForgotPassword request) {
        Optional<ForgotPassword> email = userRepository.findPassword(request.getEmail());
        if (email.isPresent()) {
            ForgotPassword reset = email.get();
            if (reset.getSecurityQuestion().equalsIgnoreCase(request.getSecurityQuestion())) {
                return "Password reset succesfully !!";
            } else {
                return "Incorrect Security Answer";
            }
        }
        return "User Not Found";
    }
}
