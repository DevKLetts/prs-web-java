package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.LineItemRepo;
import com.prs.db.ProductRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Product;
import com.prs.model.Request;

import jakarta.transaction.Transactional;


@CrossOrigin
@RestController
@RequestMapping("/api/LineItems")

public class LineItemController {
	
	// Injecting repositories to handle database operations for LineItem, Product, and Request entities
	@Autowired
	LineItemRepo lineItemRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	RequestRepo requestRepo;

	// Endpoint to retrieve all LineItems
	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		return lineItemRepo.findAll();
	}

	// Endpoint to retrieve a LineItem by its ID
	@GetMapping("/{id}")
	public Optional<LineItem> getLineItemById(@PathVariable int id) {
		Optional<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line Item ID Not Found");
        } else {
            return lineItem;
        }
	}

	// Endpoint to retrieve LineItems by Request ID
	@GetMapping("/lines-for-req/{reqId}")
	public List<LineItem> getLineItemsByRequestId(@PathVariable int reqId) {
		List<LineItem> lineItems = lineItemRepo.findByRequestId(reqId);
		if (lineItems.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Line Items found for Request ID " + reqId);
		} else {
			return lineItems;
		}
	}
	
	// Endpoint to create a new LineItem
	@PostMapping
	public LineItem createLineItem(@RequestBody LineItem lineItem) {

		lineItemRepo.save(lineItem);
		updateTotal(lineItem.getRequest().getId());
		return lineItem;
		
		
	}
	
	// Endpoint to update an existing LineItem
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
		}
		else if (lineItemRepo.existsById(lineItem.getId())) {
			lineItemRepo.save(lineItem);
			updateTotal(lineItem.getRequest().getId());	
			return ResponseEntity.noContent().build();
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
		}
	}

	// Endpoint to delete a LineItem by its ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteLineItem(@PathVariable int id) {
		if (lineItemRepo.existsById(id)) {
			// Update total for the associated request
			Optional<LineItem> lineItem = lineItemRepo.findById(id);
			lineItemRepo.deleteById(id);
			updateTotal(lineItem.get().getRequest().getId());
			return ResponseEntity.noContent().build();
		}
		else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
		}
	}


	// Method to update the total cost of a request based on its line items
	@Transactional
	public void updateTotal(int reqId) {
	    Optional<Request> request = requestRepo.findById(reqId);
	    
	    if (request.isPresent()) {
	    Request r = request.get();
	    Double total = lineItemRepo.calculateTotalByRequestId(reqId);
	    r.setTotal(total != null ? total : 0.0);
	    requestRepo.save(r);
	}
	}

}
