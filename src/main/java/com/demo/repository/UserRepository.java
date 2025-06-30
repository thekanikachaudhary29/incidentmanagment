package com.demo.repository;

import com.demo.dto.ForgotPasswordRequest;
import com.demo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User findByEmail(@Email @NotBlank String email);

    @Query("select u.email from User u where u.email = :email")
    Optional<String> findLogInAndPassword(@Email @NotBlank String email);

}
