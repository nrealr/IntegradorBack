package com.backend.mediConnect.repository;

import com.backend.mediConnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role r WHERE u.email = :email")
    public Optional<User> findByEmail(String email);

}
