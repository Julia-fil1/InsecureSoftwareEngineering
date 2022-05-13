package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.AllUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AllUsers, Long> {
    AllUsers findByUsername(String username);
    AllUsers findByEmail(String email);
    AllUsers findByPpsNumber(String ppsNumber);
}
