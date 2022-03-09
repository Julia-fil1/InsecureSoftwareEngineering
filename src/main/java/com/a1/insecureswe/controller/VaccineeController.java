package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.Forum;
import com.a1.insecureswe.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.a1.insecureswe.exception.AppointmentTakenException;
import com.a1.insecureswe.model.Appointment;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.AppointmentRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
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

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @GetMapping("/logged_in_home")
    public String loggedIn() {
        return "/vaccinee/logged_in_home";
    }

    @GetMapping("/history")
    public String history() { return "/vaccinee/history.html"; }

    @PostMapping("/set_appointment")
    public String saveAppointment(Appointment appointment) throws AppointmentTakenException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo currentUser;

        String username;
        if(principal instanceof UserInfo) {
            username = ((UserInfo) principal).getUsername();
        } else {
            username = principal.toString();
        }
        currentUser = userInfoRepository.findByUsername(username);

        // Hardcoded till I fix this
        appointment.setUserId(185L);
        //appointment.setUserId(currentUser.getId());

        /*List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment app : appointments) {
            if (app.getAppointmentDate() == appointment.getAppointmentDate() || appointment.getAppointmentDate().isBefore(LocalDate.now())) {
                throw new AppointmentTakenException(app.getAppointmentDate());
            }
        }*/
        appointmentRepository.save(appointment);
        return "vaccinee/booking_success";
    }

    @GetMapping("/book_appointment")
    public String createAppointment(Model model) {
        model.addAttribute("appointment", new Appointment());
        return "/vaccinee/book_appointment";
    }
}
