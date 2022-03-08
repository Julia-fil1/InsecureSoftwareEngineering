package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.Staff;
import com.a1.insecureswe.model.User;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.StaffRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @RequestMapping({"/"})
    public String viewHomePage(Model model){
        return "home_page.html";
    }

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserInfo());
        return "signup_form.html";
    }

    @PostMapping("/process_register")
    public String processRegister(UserInfo user) {
//        Was trying to encode password WIP -roland

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
        this.userInfoRepository.save(user);

        return "register_success.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @PostMapping(value = "/login")
    public String log(User loggingUser) {
        UserInfo user = userInfoRepository.findByUsernameAndPassword(loggingUser.getUsername(), loggingUser.getPassword());
        Staff staff = staffRepository.findByUsernameAndPassword(loggingUser.getUsername(), loggingUser.getPassword());

        if(user == null && staff == null)
            return "redirect:login";
        else if(staff != null) {
            return "redirect:logged_in_home_staff";
        }
        else
            return "redirect:logged_in_home";
    }

    @GetMapping("/logged_in_home")
    public String loggedIn() {
        return "logged_in_home.html";
    }

    @GetMapping("/logged_in_home_staff")
    public String loggedInStaff() {
        return "logged_in_home_staff.html";
    }

    @GetMapping("/history")
    public String history() { return "history.html"; }
}
