package com.prs.model;



import jakarta.persistence.GeneratedValue;

import com.prs.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class User implements Comparable<User> {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String phonenumber;
	private String email;
	private boolean Reviewer;
	private boolean Admin;

	
	// Constructor

	public User(int id, String username, String password, String firstName, String lastName, String phonenumber,
			String email, boolean Reviewer, boolean Admin) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phonenumber = phonenumber;
		this.email = email;
		this.Reviewer = Reviewer;
		this.Admin = Admin;
	}

	public User() {
		super();
	}
	
	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isReviewer() {
		return Reviewer;
	}

	public void setReviewer(boolean isReviewer) {
		this.Reviewer = isReviewer;
	}

	public boolean isAdmin() {
		return Admin;
	}

	public void setAdmin(boolean isAdmin) {
		this.Admin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phonenumber=" + phonenumber + ", email=" + email + ", isReviewer="
				+ Reviewer + ", isAdmin=" + Admin + "]";
	}

	@Override
	public int compareTo(User o) {
		if (o instanceof User) {
			User user = (User) o;
			return this.firstName.compareTo(user.firstName);
		}
		return 0;
	}


}
