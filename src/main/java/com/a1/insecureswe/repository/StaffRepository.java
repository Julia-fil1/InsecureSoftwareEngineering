package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.Staff;
import com.a1.insecureswe.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM staff s WHERE s.username = :username and s.password = :password")
    public Staff findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
