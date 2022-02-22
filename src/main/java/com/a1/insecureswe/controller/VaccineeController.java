package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.Vaccinee;
import com.a1.insecureswe.repository.VaccineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class VaccineeController {

    @Autowired
    VaccineeRepository vaccineeRepository;

    @RequestMapping({"/list"})
    public String viewAdminPage(Model model){
        List<Vaccinee> listUsers = vaccineeRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "admin_page.html";
    }
}
