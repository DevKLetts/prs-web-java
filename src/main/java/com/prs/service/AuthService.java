package com.prs.service;

import org.springframework.stereotype.Service;

import com.prs.db.UserRepo;
import com.prs.model.LoginDTO;
import com.prs.model.User;

@Service
public class AuthService {

    private  final UserRepo userRepo;

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public  boolean authenticate(LoginDTO loginDTO) {
    	User user = userRepo.findByUsername(loginDTO.getUsername());
        return user != null && user.getPassword().equals(loginDTO.getPassword());
    }

	
}
