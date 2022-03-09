package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.Forum;
import com.a1.insecureswe.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("vaccinee")
public class VaccineeController {
//    @Autowired
//    private ForumRepository forumRepository;
//
//    @GetMapping("/forum")
//    public String showForumForm(Model model) {
//        model.addAttribute("forum", new Forum());
//        List<Forum> listForum = forumRepository.findAll();
//        model.addAttribute("listForum", listForum);
//        return "/vaccinee/forum_page";
//    }
//
//    @PostMapping("/process_question")
//    public String processQuestion(Forum forum) {
//        this.forumRepository.save(forum);
//        return "redirect:forum";
//    }

    @GetMapping("/history")
    public String history() { return "/vaccinee/history.html"; }
}
