package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prs.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer> {



	List<LineItem> findAll();


	Optional<LineItem> findById(int id);
	List<LineItem> findByRequestId(int requestId);


	List<LineItem> save(List<LineItem> lineItems);
	

	//List<LineItem> update(LineItem lineItem);
	

	List<LineItem> deleteById(int id);
	
	@Query("SELECT SUM(li.quantity * p.price) FROM LineItem li " +
		       "JOIN Product p ON li.product.id = p.id " +
		       "WHERE li.request.id = :requestId AND li.product IS NOT NULL")
	Double calculateTotalByRequestId(@Param("requestId") int requestId) ;

	


	
}
