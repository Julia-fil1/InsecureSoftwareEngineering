package com.a1.insecureswe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column
    private Long userId;

    @Column
    private LocalDate appointmentDate;

    @Column
    private String location;

    public Appointment() {
        super();
    }

    public Appointment(Long id, Long userId, LocalDate date, String location) {
        super();
        this.id = id;
        this.userId = userId;
        this.appointmentDate = date;
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

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
