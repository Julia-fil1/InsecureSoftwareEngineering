package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.*;
import com.a1.insecureswe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForumRepository forumRepository;

    @RequestMapping({"/"})
    public String viewHomePage(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //      Retrieves entire userInfo DB
        List<UserInfo> listUsers = userInfoRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        //      Gets total count of users
        long totalUserCount = listUsers.toArray().length;
        model.addAttribute("totalUserCount", totalUserCount);

        //      Gets total count of Irish
        long totalIrish = userInfoRepository.findTotalIrish();
        model.addAttribute("totalIrish", totalIrish);

        if(principal.toString().contains("VACCINEE")) {
            return "redirect:/vaccinee/logged_in_home";

        } else if(principal.toString().contains("ADMIN")) {
            return "redirect:/admin/logged_in_home_staff";
        } else{
            System.out.println(principal.toString());
            return "home_page.html";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserInfo());
        return "signup_form.html";
    }

    @PostMapping("/process_register")
    public String processRegister(UserInfo user) {
        String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encoded);
        user.setRole("VACCINEE");
        user.setEnabled(1);
        this.userInfoRepository.save(user);
        this.userRepository.save(new AllUsers(user.getUsername(), user.getPassword(), user.getRole(), 1));
        //user.setIsNewUser(true);
        return "register_success.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
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

    @GetMapping("/adminOnly")
    public String only() {
        return "adminOnly.html";
    }
}
