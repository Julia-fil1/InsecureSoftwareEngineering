package com.a1.insecureswe.controller;

import com.a1.insecureswe.model.Forum;
import com.a1.insecureswe.model.User;
import com.a1.insecureswe.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.a1.insecureswe.exception.AppointmentTakenException;
import com.a1.insecureswe.model.Appointment;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.AppointmentRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

    @GetMapping("/history")
    public String history() { return "/vaccinee/history.html"; }

    @PostMapping("/set_appointment")
    public String saveAppointment(Appointment appointment, Model model) throws AppointmentTakenException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo currentUser;
        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        currentUser = userInfoRepository.findByUsername(username);
        //appointment.setUserId(currentUser.getId());

        // Ensure date isn't any earlier than today/now
        if(appointment.getAppointmentDate().isBefore(LocalDate.now()) && appointment.getAppointmentTime().isBefore(LocalTime.now())) {
            return "/vaccinee/book_appointment";
        }

        // Check to ensure user can't have more than 2 appointments (fully vaccinated)
        if(currentUser.getAppointments().size() == 2) {
            return "/logged_in_home.html";
        }

        // Check to make sure selected date & time aren't already taken at the selected location
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment app : appointments) {
            if (app.getAppointmentDate().isEqual(appointment.getAppointmentDate()) && app.getAppointmentTime().equals(appointment.getAppointmentTime()) && app.getLocation().equals(appointment.getLocation())) {
                throw new AppointmentTakenException(app.getAppointmentDate());
            }
        }

        // Adding appointment to user's list/history
        List<Appointment> userAppointments = currentUser.getAppointments();
        userAppointments.add(appointment);
        currentUser.setAppointments(userAppointments);

        appointmentRepository.save(appointment);
        return "vaccinee/booking_success";
    }

    // Thinking of displaying appointment details once booked, not done yet
    /*@GetMapping("/vaccinee/booking_success")
    public String booking_success(@PathVariable("appointment_id") Long id, Model model){
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment app : appointments) {
            if (Objects.equals(app.getId(), id)) {
                model.addAttribute(app);
            }
        }
        return "vaccinee/booking_success";
    }*/

    @GetMapping("/book_appointment")
    public String createAppointment(Model model) {
        model.addAttribute("appointment", new Appointment());

        // Setting earliest time/date of appointment to be 09:00 tomorrow
        // Last appointment allowed at 21:00 for working people throughout the week
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate maxDate= tomorrow.plusMonths(6);
        model.addAttribute("tomorrow", tomorrow);
        model.addAttribute("maxDate", maxDate);
        return "/vaccinee/book_appointment";
    }
}
