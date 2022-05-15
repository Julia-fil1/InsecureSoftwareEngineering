package com.a1.insecureswe.model;

import com.a1.insecureswe.AttributeEncryptor;

import javax.persistence.*;

@Entity(name = "all_users")
@Table(name = "all_users")
public class AllUsers implements User{
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    private String role;

    @Column(unique = true)
    private String email;

    @Convert(converter = AttributeEncryptor.class)
    @Column(unique = true)
    private String ppsNumber;

    private int enabled;

    public AllUsers(String username, String password, String role, String email, String ppsNumber, int enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.ppsNumber = ppsNumber;
        this.enabled = enabled;
    }

    public AllUsers() {
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

    @Override
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public void setPpsNumber(String ppsn) {
        this.ppsNumber = ppsn;
    }

    @Override
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
