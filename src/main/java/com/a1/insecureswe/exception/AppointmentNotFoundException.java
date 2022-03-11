package com.a1.insecureswe.exception;

public class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException(long appt_id) {
        super(String.format("User is not found with id : '%s'", appt_id));
    }
}
