package com.a1.insecureswe.repository;

import com.a1.insecureswe.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    //    @Query("SELECT v FROM vaccinees v WHERE v.username = :username and v.password = :password")
//    public UserInfo findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    UserInfo findByUsername(String username);

    @Query("select count(v.nationality) from vaccinees v where v.nationality = 'Irish'")
    Long findTotalIrish();


    @Override
    Optional<UserInfo> findById(Long aLong);
}
