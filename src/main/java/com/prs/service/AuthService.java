package com.prs.service;

import org.springframework.stereotype.Service;

import com.prs.db.UserRepo;
import com.prs.model.LoginDTO;
import com.prs.model.User;

@Service
public class AuthService {

	// This service handles authentication logic for the application
    private  final UserRepo userRepo;

    // Constructor to inject UserRepo dependency
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

	/**
	 * Authenticates a user based on the provided login credentials.
	 *
	 * @param loginDTO The login credentials containing username and password.
	 * @return true if authentication is successful, false otherwise.
	 */
    public  boolean authenticate(LoginDTO loginDTO) {
    	User user = userRepo.findByUsername(loginDTO.getUsername());
        return user != null && user.getPassword().equals(loginDTO.getPassword());
    }

	
}
