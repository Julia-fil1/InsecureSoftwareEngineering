package com.a1.insecureswe.model;

import javax.persistence.*;

@Entity(name = "staff")
@Table(name = "staff")
public class Staff implements User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private final Boolean isAdmin = true;

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

        public Boolean getAdmin() {
                return isAdmin;
        }
}
