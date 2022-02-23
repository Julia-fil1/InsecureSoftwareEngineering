package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StaffController {
    @Autowired
    UserInfoRepository userInfoRepository;

    @RequestMapping({"/list"})
    public String viewAdminPage(Model model){
        List<UserInfo> listUsers = userInfoRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "admin_page.html";
    }
}
