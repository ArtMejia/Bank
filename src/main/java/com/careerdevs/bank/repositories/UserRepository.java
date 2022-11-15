package com.careerdevs.bank.repositories;

import com.careerdevs.bank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, String> {
    Optional<User> findByLoginToken(String loginToken);
}
