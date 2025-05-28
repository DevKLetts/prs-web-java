package com.prs.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.prs.db.RequestRepo;
import com.prs.model.Request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/Requests")

public class RequestController {


	@Autowired
	private RequestRepo requestRepo;

	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Request> getRequestById(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if (request.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request ID Not Found");
		} else {
			return request;
		}

	}

	@PostMapping
	public Request createRequest(@RequestBody Request request) {
		if (request.getRequestNumber() == null || request.getRequestNumber().isEmpty()) {
			request.setRequestNumber(generateNextRequestNumber());
			request.setSubmittedDate(LocalDate.now());
			request.setStatus("New");
			request.setTotal(0.00);
			request.setReasonForRejection(null);
		}
		return requestRepo.save(request);
	}

	@PutMapping("/{id}")
	 public void update(@PathVariable int id, @RequestBody Request request) {
	  if (id != request.getId()) {
	   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie id mismatch vs URL.");
	  }
	  else if (requestRepo.existsById(request.getId())) {
	   requestRepo.save(request);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "Movie not found for id "+id);
	  }
	 } 
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
		if (!requestRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		requestRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/approve")
	public ResponseEntity<Request> approveRequest(@PathVariable int id) {
		Optional<Request> optionalRequest = requestRepo.findById(id);
		if (optionalRequest.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Request request = optionalRequest.get();
		request.setStatus("Approved");
		requestRepo.save(request);
		return ResponseEntity.ok(request);
	}
	
	
	public String generateNextRequestNumber() {
	    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	    String maxSuffix = requestRepo.findMaxRequestSuffix(today);

	    int nextNumber = Integer.parseInt(maxSuffix) + 1;
	    String newSuffix = String.format("%04d", nextNumber); // Ensures 4-digit format

	    return "R" + (today) + newSuffix;
	}

}
