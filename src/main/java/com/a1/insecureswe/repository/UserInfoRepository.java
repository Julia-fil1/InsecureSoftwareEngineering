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

    @Override
    Optional<UserInfo> findById(Long aLong);

    //    Nationality queries
    @Query("select count(v.nationality) from vaccinees v where v.nationality = 'American'")
    Long findTotalAmerican();

    @Query("select count(v.nationality) from vaccinees v where v.nationality = 'Irish'")
    Long findTotalIrish();

    @Query("select count(v.nationality) from vaccinees v where v.nationality = 'Polish'")
    Long findTotalPolish();

    @Query("select count(v.nationality) from vaccinees v where v.nationality = 'Romanian'")
    Long findTotalRomanian();

    //    Age queries
    @Query("select count(v.dob) from vaccinees v where floor(datediff(curdate(),dob)/365.2425) BETWEEN 18 AND 25")
    Long findTotalAge18_25();

    @Query("select count(v.dob) from vaccinees v where floor(datediff(curdate(),dob)/365.2425) BETWEEN 26 AND 35")
    Long findTotalAge26_35();

    @Query("select count(v.dob) from vaccinees v where floor(datediff(curdate(),dob)/365.2425) BETWEEN 36 AND 45")
    Long findTotalAge36_45();

    @Query("select count(v.dob) from vaccinees v where floor(datediff(curdate(),dob)/365.2425) BETWEEN 46 AND 55")
    Long findTotalAge46_55();

    @Query("select count(v.dob) from vaccinees v where floor(datediff(curdate(),dob)/365.2425) BETWEEN 56 AND 65")
    Long findTotalAge56_65();

    @Query("select count(v.dob) from vaccinees v where floor(datediff(curdate(),dob)/365.2425) > 65")
    Long findTotalAge65Plus();
}
