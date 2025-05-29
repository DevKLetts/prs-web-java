package com.prs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.prs.db.ProductRepo;
import com.prs.model.Product;

@CrossOrigin
@RestController
@RequestMapping("/api/Products")

public class ProductController {

	// Injecting ProductRepo to handle database operations for Product entities
	@Autowired
	private ProductRepo productRepo;

	// Endpoint to retrieve all products
	@GetMapping("/")
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	// Endpoint to retrieve a product by ID
	@GetMapping("/{id}")
	public Optional<Product> getById(@PathVariable int id) {
		Optional<Product> p = productRepo.findById(id);
		if (p.isPresent()) {
			return p;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product ID Not Found");
		}
	}

	// Endpoint to add a new product
	@PostMapping
	public Product addProduct(@RequestBody Product product) {

		return productRepo.save(product);
	}

	// Endpoint to update an existing product
	@PutMapping("/{id}")
	public void update(@PathVariable int id, @RequestBody Product product) {
		if (id != product.getId()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product id mismatch vs URL.");
		} else if (productRepo.existsById(product.getId())) {
			productRepo.save(product);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id " + id);
		}
	}

	// Endpoint to delete a product by ID
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		if (productRepo.existsById(id)) {
			productRepo.deleteById(id);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found for id " + id);
		}
	}

}
