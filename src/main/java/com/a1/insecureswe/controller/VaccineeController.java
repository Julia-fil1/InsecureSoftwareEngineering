package com.a1.insecureswe.controller;

import com.a1.insecureswe.exception.HistoryNotFoundException;
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
            currentUser.setLatestVaccinationDate(appointment.getAppointmentDate());
        }
        userInfoRepository.save(currentUser);

        model.addAttribute("appointment", appointment);
        return "vaccinee/booking_success";
    }

    @GetMapping("/book_appointment")
    public String checkAvailability(Model model) {
        UserInfo currentUser = getCurrentUser();

        // If the user has booked an appointment but hasn't received that dose yet, they can't book another appointment yet
        if((currentUser.getAppointments().size() == 1 && currentUser.getDoseNumber() == 0) ||
                (currentUser.getAppointments().size() == 1 && currentUser.getDoseNumber() == 1 && currentUser.getLatestVaccinationDate().isAfter(LocalDate.now()))) {
            if (LocalDate.now().isBefore(currentUser.getAppointments().get(0).getAppointmentDate())) {
                return "vaccinee/alreadyBooked.html";
            }
        } else if (currentUser.getDoseNumber() == 2) {
            return "vaccinee/fullyVaccinated.html";
        }

        Appointment appointment = new Appointment();
        model.addAttribute("appointment", appointment);

        LocalDate minDate;
        if(currentUser.getDoseNumber() == 1 && LocalDate.now().isBefore(currentUser.getLatestVaccinationDate().plusWeeks(3))) {
            if (LocalDate.now().isAfter(currentUser.getLatestVaccinationDate().plusWeeks(2)) && LocalDate.now().isBefore(currentUser.getLatestVaccinationDate().plusWeeks(1))) {
                minDate = LocalDate.now().plusWeeks(1);
            } else if (LocalDate.now().isAfter(currentUser.getLatestVaccinationDate().plusWeeks(1)) && LocalDate.now().isBefore(currentUser.getLatestVaccinationDate().plusWeeks(2))) {
                minDate = LocalDate.now().plusWeeks(2);
            } else {
                minDate = currentUser.getLatestVaccinationDate().plusWeeks(3);
            }
        } else {
            minDate = LocalDate.now().plusDays(1);
        }

        LocalDate maxDate = minDate.plusMonths(6);
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
        /*List<Appointment> apps = appointmentRepository.findTakenTimesFor("UCD");
        List<String> takenTimes = new ArrayList<>();
        for (Appointment appointment : apps) {
            takenTimes.add(appointment.getAppointmentTime());
        }
        model.addAttribute("takenUCDTimes", takenTimes);

        apps.clear();
        apps = appointmentRepository.findTakenTimesFor("DCU");
        takenTimes.clear();
        for (Appointment appointment : apps) {
            takenTimes.add(appointment.getAppointmentTime());
        }
        model.addAttribute("takenDCUTimes", takenTimes);

        apps.clear();
        apps = appointmentRepository.findTakenTimesFor("Ongar");
        takenTimes.clear();
        for (Appointment appointment : apps) {
            takenTimes.add(appointment.getAppointmentTime());
        }
        model.addAttribute("takenOngarTimes", takenTimes);

        apps.clear();
        apps = appointmentRepository.findTakenTimesFor("Citywest");
        takenTimes.clear();
        for (Appointment appointment : apps) {
            takenTimes.add(appointment.getAppointmentTime());
        }
        model.addAttribute("takenCitywestTimes", takenTimes);*/

        model.addAttribute("timesAvailable", times);
        model.addAttribute("appointment", appointment);

        // When do we get info on their last vaccination? -- Admin updates?
        // Do I need to store the date of the last vaccination?
        // To ensure they can't book 2 appointments -- check if 1st app date is before now
        // Diary needs to be done
        // Styling needs to be done

        return "/vaccinee/book_appointment";
    }

    @RequestMapping({"/history"})
    public String history(Model model) throws HistoryNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserInfo currentUser;
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        currentUser = userInfoRepository.findByUsername(username);

        List<Appointment> listApp = currentUser.getAppointments();
        model.addAttribute("listApp", listApp);

//        if(listApp != null) {
//            return "/vaccinee/history.html";
//        } else {
//            return "redirect:history_clean";
//        }
        return "/vaccinee/history.html";
    }

//    @GetMapping("/history_clean")
//    public String historyClean() {
//        return "/vaccinee/history_clean.html";
//    }

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
