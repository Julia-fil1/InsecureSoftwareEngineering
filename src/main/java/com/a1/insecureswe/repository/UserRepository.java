package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
