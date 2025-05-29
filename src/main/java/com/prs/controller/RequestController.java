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

	// Repository for Request entity
	@Autowired
	private RequestRepo requestRepo;

	// Get mapping to retrieve all requests
	@GetMapping("/")
	public List<Request> getAllRequests() {
		return requestRepo.findAll();
	}

	// Get mapping to retrieve a request by ID
	@GetMapping("/{id}")
	public Optional<Request> getRequestById(@PathVariable int id) {
		Optional<Request> request = requestRepo.findById(id);
		if (request.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request ID Not Found");
		} else {
			return request;
		}
	}
	
	// Get mapping to retrieve all requests as long as the user ID does not match the request's user ID
	@GetMapping("/list-review/{userId}")
	public List<Request> getRequestsForReview(@PathVariable int userId) {
        List<Request> requests = requestRepo.findByStatus("REVIEW");
;
        if (!requests.isEmpty() && requests.stream().anyMatch(r -> r.getUser().getId() != userId)) {
        		return requests;
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No requests found for review.");
        }
	}

	// Post mapping to create a new request
	@PostMapping
	public Request createRequest(@RequestBody Request request) {
		if (request.getRequestNumber() == null || request.getRequestNumber().isEmpty()) {
			if (request.getUser().getId() == 0 | request.getUser() == null ||  !requestRepo.findById(request.getUser().getId()).isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A valid User ID must be provided.");
			}
			if (request.getDateNeeded().isBefore(LocalDate.now().plusDays(1))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date needed cannot be before tomorrow.");
            }
			request.setRequestNumber(generateNextRequestNumber());
			request.setSubmittedDate(LocalDate.now());
			request.setStatus("NEW");
			request.setTotal(0.00);
			request.setReasonForRejection(null);
		}
		return requestRepo.save(request);
	}

	// Put mapping to update a request
	@PutMapping("/{id}")
	 public Request update(@PathVariable int id, @RequestBody Request request) {
	  if (id != request.getId()) {
	   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request id mismatch vs URL.");
	  }
	  else if (requestRepo.existsById(request.getId())) {
	   return requestRepo.save(request);
	  }
	  else {
	   throw new ResponseStatusException(
	     HttpStatus.NOT_FOUND, "Request not found for id "+id);
	  }
	 } 
	
	// Put mapping to submit a request for review
	// If total amount is less than or equal to 50, it is approved immediately
	@PutMapping("/submit-review/{id}")
	public ResponseEntity<Request> submitRequest(@PathVariable int id) {
		Optional<Request> optionalRequest = requestRepo.findById(id);
		Request request = optionalRequest.get();
		if(request != null) {
				
			if (request.getTotal() <= 50) {
			request.setStatus("APPROVED");;
			}
			else {
				request.setStatus("REVIEW"); 
			}
			request.setSubmittedDate(LocalDate.now());
			requestRepo.save(request);
			return ResponseEntity.ok(request);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request ID Not Found");
		}
		
	}
	
	// Put mapping to approve a request
	@PutMapping("/approve/{id}")
	public ResponseEntity<Request> approveRequest(@PathVariable int id) {
		Optional<Request> optionalRequest = requestRepo.findById(id);
		Request request = optionalRequest.get();
		request.setStatus("APPROVED");
		requestRepo.save(request);
		return ResponseEntity.ok(request);
	}
	
	// Put mapping to reject a request with a reason
	@PutMapping("/reject/{id}")
	public ResponseEntity<Request> rejectRequest(@PathVariable int id, @RequestBody String reasonForRejection) {
		Optional<Request> optionalRequest = requestRepo.findById(id);
		Request request = optionalRequest.get();
		if(request.getStatus().equalsIgnoreCase("APPROVED")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot reject an approved request");
		}else {
				request.setStatus("REJECTED");
		}
		if (reasonForRejection == null || reasonForRejection.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reason for rejection cannot be empty");
		} else {
			request.setReasonForRejection(reasonForRejection);
		}
		requestRepo.save(request);
		return ResponseEntity.noContent().build();
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRequest(@PathVariable int id) {
		if (!requestRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		requestRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	public String generateNextRequestNumber() {
	    String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
	    String maxSuffix = requestRepo.findMaxRequestSuffix(today);

	    int nextNumber = Integer.parseInt(maxSuffix) + 1;
	    String newSuffix = String.format("%04d", nextNumber); // Ensures 4-digit format

	    return "R" + (today) + newSuffix;
	}

}
