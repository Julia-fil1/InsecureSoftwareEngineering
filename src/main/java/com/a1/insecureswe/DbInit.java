package com.a1.insecureswe;

import com.a1.insecureswe.model.AllUsers;
import com.a1.insecureswe.model.Forum;
import com.a1.insecureswe.model.Staff;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private UserInfoRepository userInfoRepository;
    private StaffRepository staffRepository;
    private UserRepository userRepository;
    private ForumRepository forumRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserInfoRepository user_info_repository, StaffRepository staffRepository, UserRepository userRepository, ForumRepository forumRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = user_info_repository;
        this.staffRepository = staffRepository;
        this.userRepository = userRepository;
        this.forumRepository = forumRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userInfoRepository.deleteAll();
        this.staffRepository.deleteAll();
        this.userRepository.deleteAll();
        this.forumRepository.deleteAll();

        UserInfo user_info = new UserInfo();

//        user_info.setId(1L);
        user_info.setUsername("john");
        user_info.setPassword(passwordEncoder.encode("password"));
        user_info.setRole("VACCINEE");
        user_info.setEnabled(1);
        user_info.setName("John");
        user_info.setSurname("Doe");
        user_info.setDob(LocalDate.of(1985, 10, 25));
        user_info.setPpsNumber("1234567C");
        user_info.setAddress("10 Main Street, Dundrum, D14 A1B3");
        user_info.setPhoneNumber("087123456");
        user_info.setEmail("john.doe@gmail.com");
        user_info.setNationality("Irish");

        AllUsers newVaccineeUser = new AllUsers(user_info.getUsername(), user_info.getPassword(), user_info.getRole(), 1);

        Staff staff =  new Staff("admin", passwordEncoder.encode("password"), "ADMIN", 1);
        AllUsers newStaffUser = new AllUsers(staff.getUsername(), staff.getPassword(), staff.getRole(), 1);

        Forum forum = new Forum();

        forum.setQuestion("Is Daiana short");
        forum.setAnswer("YES!");

        List<UserInfo> user_info1 = Arrays.asList(user_info);
        List<Staff> staffList = Arrays.asList(staff);
        List<AllUsers> allUsersList = Arrays.asList(newVaccineeUser, newStaffUser);
        List<Forum> forumList = Arrays.asList(forum);

        this.userInfoRepository.saveAll(user_info1);
        this.staffRepository.saveAll(staffList);
        this.userRepository.saveAll(allUsersList);
        this.forumRepository.saveAll(forumList);
    }
}
