package com.a1.insecureswe.model;

import javax.persistence.*;

@Entity(name = "staff")
@Table(name = "staff")
public class Staff implements User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String ppsNumber;

    private String role;

    private int enabled;

    public Staff(String username, String password, String role, String email, String ppsNumber, int enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.ppsNumber = ppsNumber;
        this.enabled = enabled;
    }

    public Staff() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    @Override
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        this.ppsNumber = ppsNumber;
    }
}
