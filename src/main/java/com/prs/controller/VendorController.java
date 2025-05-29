package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.ProductRepo;
import com.prs.db.VendorRepo;
import com.prs.model.Vendor;

@CrossOrigin
@RestController
@RequestMapping("/api/Vendors")

public class VendorController {

	// Injecting VendorRepo to handle database operations for Vendor entities
	@Autowired
	private VendorRepo vendorRepo;
	@Autowired
	private ProductRepo productRepo;

	// Endpoint to retrieve all vendors
	@GetMapping("/")
	public List<Vendor> getAllVendors() {
		return vendorRepo.findAll();
	}

	// Endpoint to retrieve a vendor by ID
	@GetMapping("/{id}")
	public Optional<Vendor> getById(@PathVariable int id) {
		Optional<Vendor> v = vendorRepo.findById(id);
		if (v.isPresent()) {
			return v;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor ID Not Found");
		}
	}

	// Endpoint to add a new vendor
	@PostMapping
	public Vendor addVendor(@RequestBody Vendor vendor) {
		return vendorRepo.save(vendor);
	}

	// Endpoint to update an existing vendor
	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody Vendor vendor) {
		if (id != vendor.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vendor id mismatch vs URL.");
		} else if (vendorRepo.existsById(vendor.getId())) {
			vendorRepo.save(vendor);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id " + id);
		}
	}

	// Endpoint to delete a vendor by ID
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (vendorRepo.existsById(id) ) {
			if (productRepo.existsByVendorId(id)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						"Vendor cannot be deleted because it has associated products.");
			}else {
			vendorRepo.deleteById(id);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vendor not found for id " + id);
		}
	}

}
