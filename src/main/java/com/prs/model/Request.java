package com.prs.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Request implements Comparable<Request> {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@ManyToOne
	@JoinColumn(name = "userID", referencedColumnName = "id")
	private User user;
	
	private String requestNumber;
	private String description;
	private String justification;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateNeeded;
	private String deliveryMode;
	private String status;
	private Double total;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate submittedDate;
	private String reasonForRejection;
	
	// Constructors
	public Request() {
		super();
	}
	
	public Request(int id, User user, String requestNumber, String description, String justification,
			LocalDate dateNeeded, String deliveryMode, String status, Double total, LocalDate submittedDate,
			String reasonForRejection) {
		super();
		this.id = id;
		this.user = user;
		this.requestNumber = requestNumber;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
	}

	// Getters and Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", userID=" + user + ", requestnumber=" + requestNumber + ", description="
				+ description + ", justification=" + justification + ", dateNeeded=" + dateNeeded + ", deliveryMode="
				+ deliveryMode + ", status=" + status + ", total=" + total + ", submittedDate=" + submittedDate
				+ ", reasonForRejection=" + reasonForRejection + "]";
	}

	@Override
	public int compareTo(Request o) {
		if (o instanceof Request) {
			Request request = (Request) o;
			return this.requestNumber.compareTo(request.requestNumber);
		}
		return 0;
	}


	
	
	


}
