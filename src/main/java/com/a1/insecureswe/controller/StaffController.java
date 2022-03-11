package com.a1.insecureswe.controller;

import com.a1.insecureswe.exception.QuestionNotFoundException;
import com.a1.insecureswe.exception.UserNotFoundException;
import com.a1.insecureswe.model.Appointment;
import com.a1.insecureswe.model.Forum;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.ForumRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("admin")
public class StaffController {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    private ForumRepository forumRepository;

    @RequestMapping({"/list"})
    public String viewAdminPage(Model model){
        List<UserInfo> listUsers = userInfoRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "/admin/admin_page.html";
    }

    @GetMapping("login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/forum")
    public String showAdminForumForm(Model model) {
        model.addAttribute("forum", new Forum());
        List<Forum> listForum = forumRepository.findAll();
        model.addAttribute("listForum", listForum);
        return "/admin/admin_forum_page.html";
    }

    @PostMapping("/answer_question")
    public String processQuestion(Forum forum) throws QuestionNotFoundException {
        Forum forum1 = forumRepository.findById(forum.getId()).orElseThrow(() -> new QuestionNotFoundException(forum.getId()));
        forum1.setAnswer(forum.getAnswer());
        this.forumRepository.save(forum1);
        return "redirect:forum";
    }

    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        List<UserInfo> listUsers = userInfoRepository.findAll();

        for (UserInfo u : listUsers) {
            if (u.getId().equals(id)) {
                model.addAttribute("user", u);
                System.out.println("user:" + u.getId());
            }
        }
        return "admin/edit_user";
    }

    @PostMapping("/edit_user")
    public String update(UserInfo userInfo, @RequestParam int doseNumber) throws UserNotFoundException {
        System.out.println("id " + userInfo.getId());
        UserInfo user = userInfoRepository.findById(userInfo.getId()).orElseThrow(() -> new UserNotFoundException(userInfo.getId()));
        user.setDoseNumber(doseNumber);
        userInfoRepository.save(user);
        return "admin/edit_user_success";
    }

    @GetMapping("/logged_in_home_staff")
    public String loggedInStaff(Model model) {
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

        return "/admin/logged_in_home_staff";
    }
}
