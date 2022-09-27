package com.stackroute.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Doctor implements User{

    @Id
    private String email;
    private String password;

    public Doctor(){

    }

    public Doctor(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
