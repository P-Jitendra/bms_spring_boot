package com.example.bms_backend.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, String> {
    boolean existsByEmail(String email);
    UserInfo findByEmail(String email);
}
