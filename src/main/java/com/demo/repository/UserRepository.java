package com.demo.repository;

import com.demo.dto.ForgotPassword;
import com.demo.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    String findByEmail(@Email @NotBlank String email);

    @Query("select u.email from User u where u.email = :email")
    Optional<User> findLogInAndPassword(@Email @NotBlank String email);

    Optional<ForgotPassword> findPassword(@Email @NotBlank String email);
}
