package com.a1.insecureswe;

import com.a1.insecureswe.model.Staff;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.StaffRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private UserInfoRepository userInfoRepository;
    private StaffRepository staffRepository;

    public DbInit(UserInfoRepository user_info_repository, StaffRepository staffRepository) {
        this.userInfoRepository = user_info_repository;
        this.staffRepository = staffRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userInfoRepository.deleteAll();
        this.staffRepository.deleteAll();

        UserInfo user_info = new UserInfo();

//        user_info.setId(1L);
        user_info.setUsername("john");
        user_info.setPassword("password");
        user_info.setName("John");
        user_info.setSurname("Doe");
        user_info.setDob(LocalDate.of(1985, 10, 25));
        user_info.setPpsNumber("1234567C");
        user_info.setAddress("10 Main Street, Dundrum, D14 A1B3");
        user_info.setPhoneNumber("087123456");
        user_info.setEmail("john.doe@gmail.com");
        user_info.setNationality("Irish");

        Staff staff =  new Staff();

//        staff.setId(1L);
        staff.setUsername("admin");
        staff.setPassword("password");

        List<UserInfo> user_info1 = Arrays.asList(user_info);
        List<Staff> staffList = Arrays.asList(staff);

        this.userInfoRepository.saveAll(user_info1);
        this.staffRepository.saveAll(staffList);
    }
}
