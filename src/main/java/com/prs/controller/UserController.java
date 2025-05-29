package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.UserRepo;
import com.prs.model.*;
import com.prs.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/Users")

public class UserController {

	// Injecting UserRepo to handle database operations for User entities
	@Autowired
	private UserRepo userRepo;
	
	// Injecting AuthService to handle authentication logic
	@Autowired
	private AuthService authService;

	// Endpoint to retrieve all users
	@GetMapping("/")	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	// Endpoint to retrieve a user by ID
	@GetMapping("/{id}")
	public Optional<User> getById(@PathVariable int id) {
		Optional<User> u = userRepo.findById(id);
		if(u.isPresent()) {
			return u;}
			else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User ID Not Found");
			}
	}
	
	@PostMapping
	public User addUser(@RequestBody User user) {
		return userRepo.save(user);
	}
	
    @PostMapping("/login")
    public LoginDTO login(@RequestBody LoginDTO loginDTO) {
        boolean isAuthenticated = authService.authenticate(loginDTO);
        
        if (isAuthenticated) {
            return loginDTO;
        }
        else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
    }
        
	
	 @PutMapping("/{id}")
	 public void update(@PathVariable int id, @RequestBody User user) {
	  if (id != user.getId()) {
	   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id mismatch vs URL.");
	  }
	  else if (userRepo.existsById(user.getId())) {
	   userRepo.save(user);
	  }
	  else {
	   throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id "+id);
	  }
	 }

	 @DeleteMapping("/{id}")
	 public void delete(@PathVariable int id) {
	  if (userRepo.existsById(id)) {
	   userRepo.deleteById(id);
	  }
	  else {
	   throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found for id "+id);
	  }
	 }

}
