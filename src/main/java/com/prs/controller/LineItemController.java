package com.prs.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prs.db.LineItemRepo;
import com.prs.model.LineItem;


@CrossOrigin
@RestController
@RequestMapping("/lineitems")

public class LineItemController {
	
	@Autowired
	private LineItemRepo lineItemRepo;

	@GetMapping
	public List<LineItem> getAllLineItems() {
		
		return lineItemRepo.findAll();
	}

	@GetMapping("/{id}")
	public List<LineItem> getLineItemById(@PathVariable int id) {
		List<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isEmpty()) {
            return null;
        } else {
            return lineItem;
        }
	}
	
	@PostMapping
	public LineItem createLineItem(@RequestBody LineItem lineItem) {
		return lineItemRepo.save(lineItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LineItem> updateLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (!lineItemRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		lineItem.setId(id);
		lineItemRepo.save(lineItem);
		return ResponseEntity.ok(lineItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLineItem(@PathVariable int id) {
		if (!lineItemRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		lineItemRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
