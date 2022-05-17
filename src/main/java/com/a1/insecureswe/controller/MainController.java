package com.a1.insecureswe.controller;

import com.a1.insecureswe.security.LoginAttemptService;
import com.a1.insecureswe.model.*;
import com.a1.insecureswe.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private static final Logger insecureLogger = LogManager.getLogger(MainController.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        String errorMessage = null;
        model.addAttribute("user", new UserInfo());
        model.addAttribute("errorMessage", errorMessage);
        return "signup_form.html";
    }

    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute("user") UserInfo user, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            System.out.println("An account associated with this username has already been created.");
            String errorMessage = "An account associated with this username has already been created.";
            model.addAttribute("errorMessage", errorMessage);
            return "signup_form.html";
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            System.out.println("An account associated with this email address has already been created.");
            String errorMessage = "An account associated with this email address has already been created.";
            model.addAttribute("errorMessage", errorMessage);
            return "signup_form.html";
        } else if (userRepository.findByPpsNumber(user.getPpsNumber()) != null) {
            System.out.println("An account associated with this PPS number has already been created.");
            String errorMessage = "An account associated with this PPS number has already been created.";
            model.addAttribute("errorMessage", errorMessage);
            return "signup_form.html";
        }

        String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encoded);
        user.setRole("VACCINEE");
        user.setEnabled(1);
        this.userInfoRepository.save(user);
        this.userRepository.save(new AllUsers(user.getUsername(), user.getPassword(), user.getRole(), user.getEmail(), user.getPpsNumber(), 1));
        insecureLogger.info("New user: " + user.getUsername() + " has been created successfully.");
        return "register_success.html";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, @RequestParam("error" ) final Optional<String> error) {
        error.ifPresent(e -> {
            model.addAttribute("error", e);
        });
        if (loginAttemptService.isBlocked(request.getRemoteAddr())) {
            insecureLogger.info("IP " + request.getRemoteAddr() + " is currently blocked.");
            return "blocked.html";
        } else {
            return "login.html";
        }
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
        insecureLogger.info("Unauthorised account has been prevented from accessing admin page.");
        return "adminOnly.html";
    }

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
            return "home_page.html";
        }
    }
}
