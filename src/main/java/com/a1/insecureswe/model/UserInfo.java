package com.a1.insecureswe.model;

import com.a1.insecureswe.AttributeEncryptor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity(name = "vaccinees")
@Table(name = "vaccinees")
public class UserInfo implements User{
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    private String role;

    private int enabled;

    @Column
    private String name;

    @Column
    private String surname;

    // @Convert(converter = AttributeEncryptor.class)
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Convert(converter = AttributeEncryptor.class)
    @Column(unique = true)
    private String ppsNumber;

    @Convert(converter = AttributeEncryptor.class)
    @Column
    private String address;

    @Convert(converter = AttributeEncryptor.class)
    @Column
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column
    private String nationality;

    @OneToMany(targetEntity = Appointment.class, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private List<Appointment> appointments;

    private int doseNumber;

    @Column
    private LocalDate latestVaccinationDate;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getName() {
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
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        if (Period.between(dob, LocalDate.now()).getYears() >= 18) {
            this.dob = dob;
        }
    }

    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        if(ppsNumber.matches("^[0-9]{7}[A-Z]{1,2}$")) {
            this.ppsNumber = ppsNumber;
        }

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public int getDoseNumber() {
        return doseNumber;
    }

    public void setDoseNumber(int doseNumber) {
        if (doseNumber > this.doseNumber) {
            this.doseNumber = doseNumber;
        }
    }

    public LocalDate getLatestVaccinationDate() {
        return latestVaccinationDate;
    }

    public void setLatestVaccinationDate(LocalDate latestVaccinationDate) {
        this.latestVaccinationDate = latestVaccinationDate;
    }
}
