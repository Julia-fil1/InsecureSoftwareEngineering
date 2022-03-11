package com.a1.insecureswe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.a1.insecureswe.model.Appointment;
import com.a1.insecureswe.model.UserInfo;
import com.a1.insecureswe.repository.AppointmentRepository;
import com.a1.insecureswe.repository.UserInfoRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;

@Controller
@RequestMapping("vaccinee")
public class VaccineeController {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @PostMapping("/set_appointment")
    public String saveAppointment(Model model, Appointment appointment) {
        UserInfo currentUser = getCurrentUser();

        // Adding appointment to user's list/history
        List<Appointment> userAppointments = currentUser.getAppointments();
        userAppointments.add(appointment);
        currentUser.setAppointments(userAppointments);

        appointmentRepository.save(appointment);

        // Make the date of the new appointment the person's latestVaccinationDate
        if (currentUser.getAppointments().size() == 1) {
            currentUser.setLatestVaccinationDate(appointment.getAppointmentDate());
        } else if (currentUser.getAppointments().size() == 2) {
            currentUser.setLatestVaccinationDate(currentUser.getAppointments().get(1).getAppointmentDate());
        }
        userInfoRepository.save(currentUser);

        model.addAttribute("appointment", appointment);
        return "vaccinee/booking_success";
    }

    @GetMapping("/book_appointment")
    public String checkAvailability(Model model) {
        UserInfo currentUser = getCurrentUser();

        // If the user has booked an appointment but hasn't received that dose yet, they can't book another appointment yet
        // Alternatively, if a user has had a dose before and then books their second appointment, they can't book another one after that
        if((currentUser.getAppointments().size() == 1 && currentUser.getDoseNumber() == 0) ||
                (currentUser.getAppointments().size() == 1 && currentUser.getDoseNumber() == 1 && currentUser.getLatestVaccinationDate().isAfter(LocalDate.now()))) {
            if (LocalDate.now().isBefore(currentUser.getAppointments().get(0).getAppointmentDate())) {
                return "vaccinee/alreadyBooked.html";
            }
        } else if (currentUser.getDoseNumber() == 2) {
            // If a user has had 2 doses, they're fully vaccinated and can't book another appointment
            return "vaccinee/fullyVaccinated.html";
        }

        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);

        LocalDate minDate;
        // If the user is booking an appointment during the three weeks after the 1st dose, this statement will prevent them booking too close to the 1st dose
        if(currentUser.getDoseNumber() == 1 && LocalDate.now().isBefore(currentUser.getLatestVaccinationDate().plusWeeks(3))) {
            if (LocalDate.now().isAfter(currentUser.getLatestVaccinationDate().plusWeeks(2)) && LocalDate.now().isBefore(currentUser.getLatestVaccinationDate().plusWeeks(1))) {
                // If it's been 2 weeks since the 1st dose, they can't book an appointment any earlier than a week after today
                minDate = LocalDate.now().plusWeeks(1);
            } else if (LocalDate.now().isAfter(currentUser.getLatestVaccinationDate().plusWeeks(1)) && LocalDate.now().isBefore(currentUser.getLatestVaccinationDate().plusWeeks(2))) {
                // If it's been 1 weeks since the 1st dose, they can't book an appointment any earlier than 2 weeks after today
                minDate = LocalDate.now().plusWeeks(2);
            } else {
                // Otherwise, they can't book an appointment any earlier than 3 weeks after today
                minDate = currentUser.getLatestVaccinationDate().plusWeeks(3);
            }
        } else {
            minDate = LocalDate.now().plusDays(1);
        }

        // Limiting it so users can only book at most 6 months in advance
        LocalDate maxDate = LocalDate.now().plusMonths(6);
        model.addAttribute("minDate", minDate);
        model.addAttribute("maxDate", maxDate);

        return "vaccinee/checkForAvailability.html";
    }

    @PostMapping("/check_availability")
    public String createAppointment(Model model, Appointment appointment) {
        List<String> takenTimes = appointmentRepository.findTakenTimesFor(appointment.getLocation(), appointment.getAppointmentDate());

        // Removing taken time slots
        ArrayList<String> times = new ArrayList<>(Arrays.asList("09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"));
        if (!takenTimes.isEmpty()) {
            times.removeAll(takenTimes);
        }

        model.addAttribute("timesAvailable", times);
        model.addAttribute("appointment", appointment);

        // When do we get info on their last vaccination? -- Admin updates?

        return "/vaccinee/book_appointment";
    }

    @GetMapping("/history")
    public String history(Model model) {
        UserInfo currentUser = getCurrentUser();
        List<Appointment> listApp = currentUser.getAppointments();
        model.addAttribute("listApp", listApp);

        if(listApp.isEmpty()) {
            return "/vaccinee/history_clean";
        } else {
            return "/vaccinee/history.html";
        }
    }

    @GetMapping("/logged_in_home")
    public String loggedIn(Model model) {
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

        return "/vaccinee/logged_in_home";
    }

    // SHort method to fetch user that's currently logged in
    private UserInfo getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if(principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return userInfoRepository.findByUsername(username);
    }
}
