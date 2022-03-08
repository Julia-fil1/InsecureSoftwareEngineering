package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.*;
import com.a1.insecureswe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserInfo());
        return "signup_form.html";
    }

    @PostMapping("/process_register")
    public String processRegister(UserInfo user) {
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

    @GetMapping("/forum")
    public String showForumForm(Model model) {
        model.addAttribute("forum", new Forum());
        List<Forum> listForum = forumRepository.findAll();
        model.addAttribute("listForum", listForum);
        return "forum_page.html";
    }

    @PostMapping("/process_question")
    public String processQuestion(Forum forum) {
        this.forumRepository.save(forum);
        return "redirect:forum";
    }
}
