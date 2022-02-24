package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.UserInfo;
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

        userInfoRepository.save(user);

        return "register_success.html";
    }
}
