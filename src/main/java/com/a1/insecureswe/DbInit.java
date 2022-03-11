package com.a1.insecureswe;

import com.a1.insecureswe.model.AllUsers;
import com.a1.insecureswe.model.Forum;
import com.a1.insecureswe.model.Staff;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
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

//-------------------------- USERINFO --------------------------
        UserInfo user_info = new UserInfo();

//        user_info.setId(1L);
        user_info.setUsername("john");
        user_info.setPassword(passwordEncoder.encode("password"));
        user_info.setRole("VACCINEE");
        user_info.setEnabled(1);
        user_info.setName("John");
        user_info.setSurname("Doe");
        user_info.setDob(LocalDate.of(1985, 10, 25));
        user_info.setPpsNumber("1234567A");
        user_info.setAddress("10 Main Street, Dundrum, D14 A1B3");
        user_info.setPhoneNumber("087123456");
        user_info.setEmail("john.doe@gmail.com");
        user_info.setNationality("Irish");

        UserInfo user_info_2 = new UserInfo();

//        user_info.setId(1L);
        user_info_2.setUsername("jane");
        user_info_2.setPassword(passwordEncoder.encode("password"));
        user_info_2.setRole("VACCINEE");
        user_info_2.setEnabled(1);
        user_info_2.setName("jane");
        user_info_2.setSurname("Doe");
        user_info_2.setDob(LocalDate.of(2000, 10, 25));
        user_info_2.setPpsNumber("1234567B");
        user_info_2.setAddress("1 Main Road, Dublin, D02 XY61");
        user_info_2.setPhoneNumber("087123454");
        user_info_2.setEmail("jane.doe@gmail.com");
        user_info_2.setNationality("Romanian");

        AllUsers newVaccineeUser = new AllUsers(user_info.getUsername(), user_info.getPassword(), user_info.getRole(), 1);

        Staff staff =  new Staff("admin", passwordEncoder.encode("password"), "ADMIN", 1);
        AllUsers newStaffUser = new AllUsers(staff.getUsername(), staff.getPassword(), staff.getRole(), 1);

// -------------------------- FORUM --------------------------
        Forum forum_1 = new Forum();

        forum_1.setQuestion("Are Covid vacinnees safe?");
        forum_1.setAnswer("Vaccines are the safest way to prevent infectious diseases. They teach your immune system " +
                "(your body's natural defences) how to protect you from a specific virus.");

        Forum forum_2 = new Forum();

        forum_2.setQuestion("Can I get a different vaccination type than the first one I received?");
        forum_2.setAnswer("Studies have found that the immune response after getting different vaccines may be as good " +
                "as getting the same vaccine. In some cases, it can be better.");

        Forum forum_3 = new Forum();

        forum_3.setQuestion("Can I still get Covid after I have been vaccinated?");
        forum_3.setAnswer("You can still get COVID-19 after vaccination. But being vaccinated can reduce how serious " +
                "your symptoms will be.");

        Forum forum_4 = new Forum();

        forum_4.setQuestion("Unanswered test question. Just enter the id and input your answer.");
        forum_4.setAnswer("");

        List<UserInfo> user_info1 = Arrays.asList(user_info,user_info_2);
        List<Staff> staffList = Arrays.asList(staff);
        List<AllUsers> allUsersList = Arrays.asList(newVaccineeUser, newStaffUser);
        List<Forum> forumList = Arrays.asList(forum_1,forum_2,forum_3,forum_4);

        this.userInfoRepository.saveAll(user_info1);
        this.staffRepository.saveAll(staffList);
        this.userRepository.saveAll(allUsersList);
        this.forumRepository.saveAll(forumList);
    }
}
