package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Product;
import com.prs.model.Vendor;

public interface ProductRepo extends JpaRepository<Product, Integer> { 

    Optional<Product> findById(int id);
    
    List<Product> findAll();

    List<Product> save(List<Product> products);

    //List<Product> update(Product product);

    List<Product> deleteById(int id);

	List<Vendor> findByVendorId(int id);

	boolean existsByVendorId(int id);


	 
}
