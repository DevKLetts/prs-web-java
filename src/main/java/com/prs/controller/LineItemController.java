package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@Autowired
	LineItemRepo lineItemRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired	
	RequestRepo requestRepo;

	@GetMapping("/")
	public List<LineItem> getAllLineItems() {
		return lineItemRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<LineItem> getLineItemById(@PathVariable int id) {
		Optional<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Line Item ID Not Found");
        } else {
            return lineItem;
        }
	}

	@GetMapping("/lines-for-req/{reqId}")
	public List<LineItem> getLineItemsByRequestId(@PathVariable int reqId) {
		List<LineItem> lineItems = lineItemRepo.findByRequestId(reqId);
		if (lineItems.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Line Items found for Request ID " + reqId);
		} else {
			return lineItems;
		}
	}
	
	@PostMapping
	public LineItem createLineItem(@RequestBody LineItem lineItem) {
		Request request = requestRepo.findById(lineItem.getRequest().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found"));
		
		Product product = productRepo.findById(lineItem.getProduct().getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
		lineItem.setRequest(request);
		lineItem.setProduct(product);
		
		updateTotal(request.getId());
		return 
		lineItemRepo.save(lineItem);
	}


	
	
	
	@PutMapping("/{id}")
	public void updateLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (id != lineItem.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LineItem id mismatch vs URL.");
		}
		else if (lineItemRepo.existsById(lineItem.getId())) {
			lineItemRepo.save(lineItem);
			updateTotal(lineItem.getRequest().getId());		
		}
		else {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteLineItem(@PathVariable int id) {
		if (lineItemRepo.existsById(id)) {
			// Update total for the associated request
			Optional<LineItem> lineItem = lineItemRepo.findById(id);
			int reqId = lineItem.get().getRequest().getId();
			lineItemRepo.deleteById(id);
			updateTotal(reqId);
		}
		else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "LineItem not found for id "+id);
		}
	}


	
	@Transactional
	public void updateTotal(int reqId) {
	    Optional<Request> request = requestRepo.findById(reqId);
	    
	    if (request.isPresent()) {
	    Request r = request.get();
	    Double total = lineItemRepo.calculateTotalByRequestId(reqId);
	    r.setTotal(total);
	    requestRepo.save(r);
	}
	}

}
