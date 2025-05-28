package com.prs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LoginDTO {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
    private String userName;
    private String password;

    // Default constructor
    public LoginDTO() {}

    // Parameterized constructor
    public LoginDTO(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    // Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUseName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}