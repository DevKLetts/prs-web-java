package com.prs.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.prs.db.RequestRepo;
import com.prs.model.Request;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/api/request")

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
			return null;
		} else {
			return request;
		}

	}

	@PostMapping
	public Request createRequest(@RequestBody Request request) {
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
	
}
