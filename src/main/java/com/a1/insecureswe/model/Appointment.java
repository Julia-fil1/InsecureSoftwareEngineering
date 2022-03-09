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
    private Long userId;

    /*@Column
    private String name;

    @Column
    private String surname;*/

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;

    @Column
    private String location;

    public Appointment() {
        super();
    }

    public Appointment(Long id, Long userId, LocalDate date, LocalTime time, String location) {
        super();
        this.id = id;
        this.userId = userId;
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }*/

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
