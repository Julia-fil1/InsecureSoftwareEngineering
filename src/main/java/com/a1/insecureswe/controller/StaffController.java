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

    @GetMapping("/logged_in_home_staff")
    public String loggedInStaff(Model model) {
        //      Retrieves entire userInfo DB
        List<UserInfo> listUsers = userInfoRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        //      Gets total count of users
        long totalUserCount = listUsers.toArray().length;
        model.addAttribute("totalUserCount", totalUserCount);

        //      Gets total count of Irish
        long totalIrish = userInfoRepository.findTotalIrish();
        model.addAttribute("totalIrish", totalIrish);

        return "/admin/logged_in_home_staff";
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
}
