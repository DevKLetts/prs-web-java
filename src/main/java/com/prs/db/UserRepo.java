package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	// - findAllUsers
	List<User> findAll();
	// - findUserById
	Optional<User> findById(int id);
		// - saveUser
	List<User> save(List<User> users);
	// - updateUser
	//List<User> update(User user);
	// - deleteUser
	List<User> deleteById(int id);
	
}
