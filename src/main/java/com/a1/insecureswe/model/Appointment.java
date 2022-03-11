package com.a1.insecureswe.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @Column
    //@DateTimeFormat(pattern = "HH:mm")
    private String appointmentTime;

    @Column
    private String location;

    @Column
    private String vaccineType;

    public Appointment() {
        super();
    }

    public Appointment(Long id, LocalDate date, String time, String location, String vaccineType) {
        super();
        this.id = id;
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.location = location;
        this.vaccineType = vaccineType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }
}
