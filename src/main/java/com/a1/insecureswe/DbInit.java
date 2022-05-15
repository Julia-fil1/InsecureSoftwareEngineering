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
        user_info.setPassword(passwordEncoder.encode("PassWord1@"));
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

        user_info_2.setUsername("jane");
        user_info_2.setPassword(passwordEncoder.encode("PassWord1@"));
        user_info_2.setRole("VACCINEE");
        user_info_2.setEnabled(1);
        user_info_2.setName("Jane");
        user_info_2.setSurname("Doe");
        user_info_2.setDob(LocalDate.of(2000, 10, 25));
        user_info_2.setPpsNumber("1234567B");
        user_info_2.setAddress("1 Main Road, Dublin, D02 XY61");
        user_info_2.setPhoneNumber("087123454");
        user_info_2.setEmail("jane.doe@gmail.com");
        user_info_2.setNationality("Romanian");

        UserInfo user_info_3 = new UserInfo();

        user_info_3.setUsername("mark");
        user_info_3.setPassword(passwordEncoder.encode("PassWord1@"));
        user_info_3.setRole("VACCINEE");
        user_info_3.setEnabled(1);
        user_info_3.setName("Mark");
        user_info_3.setSurname("Smith");
        user_info_3.setDob(LocalDate.of(1968, 10, 21));
        user_info_3.setPpsNumber("1234567C");
        user_info_3.setAddress("15 Main Road, Dublin, D02 XY68");
        user_info_3.setPhoneNumber("087123467");
        user_info_3.setEmail("mark.smith@gmail.com");
        user_info_3.setNationality("English");

        UserInfo user_info_4 = new UserInfo();

        user_info_4.setUsername("mary");
        user_info_4.setPassword(passwordEncoder.encode("PassWord1@"));
        user_info_4.setRole("VACCINEE");
        user_info_4.setEnabled(1);
        user_info_4.setName("Mary");
        user_info_4.setSurname("Johnson");
        user_info_4.setDob(LocalDate.of(1972, 12, 21));
        user_info_4.setPpsNumber("1234567D");
        user_info_4.setAddress("46 Main Road, Clongriffin, D15 XY68");
        user_info_4.setPhoneNumber("087123222");
        user_info_4.setEmail("mary.johnson@gmail.com");
        user_info_4.setNationality("American");

        AllUsers newVaccineeUser = new AllUsers(user_info.getUsername(), user_info.getPassword(), user_info.getRole(), user_info.getEmail(),user_info.getPpsNumber(), 1);
        AllUsers newVaccineeUser2 = new AllUsers(user_info_2.getUsername(), user_info_2.getPassword(), user_info_2.getRole(),user_info_2.getEmail(),user_info_2.getPpsNumber(), 1);
        AllUsers newVaccineeUser3 = new AllUsers(user_info_3.getUsername(), user_info_3.getPassword(), user_info_3.getRole(),user_info_3.getEmail(),user_info_3.getPpsNumber(), 1);
        AllUsers newVaccineeUser4 = new AllUsers(user_info_4.getUsername(), user_info_4.getPassword(), user_info_4.getRole(),user_info_4.getEmail(),user_info_4.getPpsNumber(), 1);

        Staff staff =  new Staff("admin", passwordEncoder.encode("PassWord1@"), "ADMIN", "admin@yahoo.com", "1234567Z", 1);
        AllUsers newStaffUser = new AllUsers(staff.getUsername(), staff.getPassword(), staff.getRole(), staff.getEmail(), staff.getPpsNumber(), 1);

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

        List<UserInfo> user_info1 = Arrays.asList(user_info,user_info_2, user_info_3, user_info_4);
        List<Staff> staffList = Arrays.asList(staff);
        List<AllUsers> allUsersList = Arrays.asList(newVaccineeUser, newVaccineeUser2, newVaccineeUser3, newVaccineeUser4, newStaffUser);
        List<Forum> forumList = Arrays.asList(forum_1,forum_2,forum_3,forum_4);

        this.userInfoRepository.saveAll(user_info1);
        this.staffRepository.saveAll(staffList);
        this.userRepository.saveAll(allUsersList);
        this.forumRepository.saveAll(forumList);
    }
}
