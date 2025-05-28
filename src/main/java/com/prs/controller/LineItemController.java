package com.prs.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.prs.db.LineItemRepo;
import com.prs.db.RequestRepo;
import com.prs.model.LineItem;
import com.prs.model.Request;

import jakarta.transaction.Transactional;


@CrossOrigin
@RestController
@RequestMapping("/lineitems")

public class LineItemController {
	
	@Autowired
	private static LineItemRepo lineItemRepo;
	private static RequestRepo requestRepo;

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
		

		
		
		updateTotal(lineItem.getId());
		lineItemRepo.save(lineItem);
		return ResponseEntity.ok(lineItem).getBody();
	}

	@PutMapping("/{id}")
	public ResponseEntity<LineItem> updateLineItem(@PathVariable int id, @RequestBody LineItem lineItem) {
		if (!lineItemRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		lineItem.setId(id);
		lineItemRepo.save(lineItem);
		updateTotal(lineItem.getId());
		return ResponseEntity.ok(lineItem);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLineItem(@PathVariable int id) {
		if (!lineItemRepo.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		lineItemRepo.deleteById(id);
		// Update total for the associated request
		List<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem != null) {
			updateTotal(id);
		}
		return ResponseEntity.noContent().build();
	}

	
	@Transactional
	public void updateTotal(int reqId) {
	    Request request = RequestRepo.findById(reqId).orElse(null);
	    if (request == null) return;

	    Double total = lineItemRepo.calculateTotalByRequestId(reqId);
	    
	    request.setTotal(total);
	    requestRepo.save(request);
	}


}
