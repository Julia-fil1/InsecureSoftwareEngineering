package com.a1.insecureswe.exception;

import java.time.LocalDate;

public class AppointmentTakenException extends Exception {
    private LocalDate date;
    public AppointmentTakenException(LocalDate date) {
        super(String.format("Appointment at %s is already taken, please select again.", date));
    }
}
