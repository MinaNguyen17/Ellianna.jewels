package com.ellianna.repository;

import com.ellianna.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    public User findByEmail(String email);
    public Optional<User> findByUserName(String username);
}
