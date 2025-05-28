package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prs.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	// - findAllUsers
	List<User> findAll();
	
	// - findUserById
	Optional<User> findById(int id);
	
	// - findUserByUserNameAndPassword
	@Query(value = "SELECT username, password FROM users WHERE username = :username", nativeQuery = true)
	List<Object[]> findByUserNameAndPassword(@Param("username") String username);
	


		// - saveUser
	List<User> save(List<User> users);
	
	// - updateUser
	//List<User> update(User user);
	
	// - deleteUser
	List<User> deleteById(int id);

	User findByUserName(String userName);

	
}
