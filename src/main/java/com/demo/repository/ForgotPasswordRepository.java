package com.demo.repository;

import com.demo.dto.ForgotPasswordRequest;
import com.demo.entity.ForgotPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    Optional<ForgotPasswordRequest> findByEmailAndSecurityQuestionAndSecurityAnswer(String securityQuestion, String securityAnswer, @Email @NotBlank String email);

}