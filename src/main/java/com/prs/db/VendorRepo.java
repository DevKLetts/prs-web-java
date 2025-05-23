package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, Integer> {
	// Custom query methods can be defined here if needed
	// For example, to find movies by title:

	
	Optional<Vendor> findById(int id);
	List<Vendor> findAll();
	List<Vendor> save(List<Vendor> vendors);

	//List<Vendor> update(Vendor vendor);
	List<Vendor> deleteById(int id);
	
	
}
