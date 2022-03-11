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

        //      Gets total count of users
        List<UserInfo> listUsers = userInfoRepository.findAll();
        long totalUserCount = listUsers.toArray().length;
        model.addAttribute("totalUserCount", totalUserCount);

        //  NATIONALITY STAT CALLS
        //      American
        long totalAmerican = userInfoRepository.findTotalAmerican();
        model.addAttribute("totalAmerican", totalAmerican);

        //      Belgian
        long totalBelgian = userInfoRepository.findTotalBelgian();
        model.addAttribute("totalBelgian", totalBelgian);

        //      Danish
        long totalDanish = userInfoRepository.findTotalDanish();
        model.addAttribute("totalDanish", totalDanish);

        //      English
        long totalEnglish = userInfoRepository.findTotalEnglish();
        model.addAttribute("totalEnglish", totalEnglish);

        //      German
        long totalGerman = userInfoRepository.findTotalGerman();
        model.addAttribute("totalGerman", totalGerman);

        //      Irish
        long totalIrish = userInfoRepository.findTotalIrish();
        model.addAttribute("totalIrish", totalIrish);

        //      Italian
        long totalItalian = userInfoRepository.findTotalItalian();
        model.addAttribute("totalItalian", totalItalian);

        //      Polish
        long totalPolish = userInfoRepository.findTotalPolish();
        model.addAttribute("totalPolish", totalPolish);

        //      Portuguese
        long totalPortuguese = userInfoRepository.findTotalPortuguese();
        model.addAttribute("totalPortuguese", totalPortuguese);

        //      Romanian
        long totalRomanian = userInfoRepository.findTotalRomanian();
        model.addAttribute("totalRomanian", totalRomanian);

        //     Spanish
        long totalSpanish = userInfoRepository.findTotalSpanish();
        model.addAttribute("totalSpanish", totalSpanish);

        //      Other
        long totalOther = userInfoRepository.findTotalOther();
        model.addAttribute("totalOther", totalOther);

        //  AGE STAT CALLS
        long totalAge18_25 = userInfoRepository.findTotalAge18_25();
        model.addAttribute("totalAge18_25", totalAge18_25);

        long totalAge26_35 = userInfoRepository.findTotalAge26_35();
        model.addAttribute("totalAge26_35", totalAge26_35);

        long totalAge36_45 = userInfoRepository.findTotalAge36_45();
        model.addAttribute("totalAge36_45", totalAge36_45);

        long totalAge46_55 = userInfoRepository.findTotalAge46_55();
        model.addAttribute("totalAge46_55", totalAge46_55);

        long totalAge56_65 = userInfoRepository.findTotalAge56_65();
        model.addAttribute("totalAge56_65", totalAge56_65);

        long totalAge65Plus = userInfoRepository.findTotalAge65Plus();
        model.addAttribute("totalAge65Plus", totalAge65Plus);

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

    @GetMapping("/history")
    public String history() { return "history.html"; }

    @GetMapping("/adminOnly")
    public String only() {
        return "adminOnly.html";
    }
}
