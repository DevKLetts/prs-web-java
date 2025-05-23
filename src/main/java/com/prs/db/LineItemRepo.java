package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer> {



	List<LineItem> findAll();


	List<LineItem> findById(int id);


	List<LineItem> save(List<LineItem> lineItems);
	

	//List<LineItem> update(LineItem lineItem);
	

	List<LineItem> deleteById(int id);
	
}
