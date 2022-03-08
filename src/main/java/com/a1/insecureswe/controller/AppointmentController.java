package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.Appointment;
import com.a1.insecureswe.exception.AppointmentTakenException;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.AppointmentRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @GetMapping("/set_appointment/{appdate}")
    public String saveAppointment(/*@ModelAttribute("datetime") LocalDate date, */@PathVariable LocalDate appdate) throws AppointmentTakenException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appdate);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo currentUser;

        String username;
        if(principal instanceof UserInfo) {
            username = ((UserInfo) principal).getUsername();
        } else {
            username = principal.toString();
        }
        currentUser = userInfoRepository.findByUsername(username);

        appointment.setUserId(currentUser.getId());
        // appointment.setName(currentUser.getName());

        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment app : appointments) {
            if (app.getAppointmentDate() == appointment.getAppointmentDate() || appointment.getAppointmentDate().isBefore(LocalDate.now())) {
                throw new AppointmentTakenException(app.getAppointmentDate());
            }
        }
        appointmentRepository.save(appointment);
        return "home_page.html";
    }

    @GetMapping("/book_appointment")
    public String createAppointment(Model model) {
        LocalDate now = LocalDate.now();
        model.addAttribute("now", now);
        return "book_appointment";
    }
}
