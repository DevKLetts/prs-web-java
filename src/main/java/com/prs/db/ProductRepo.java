package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> { 
    // Custom query methods can be defined here if needed
    // For example, to find movies by title:

    Optional<Product> findById(int id);

    List<Product> findAll();

    List<Product> save(List<Product> products);

   //List<Product> update(Product product);

    List<Product> deleteById(int id);
}
