package com.prs.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prs.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Integer> {



	List<LineItem> findAll();


	List<LineItem> findById(int id);


	List<LineItem> save(List<LineItem> lineItems);
	

	//List<LineItem> update(LineItem lineItem);
	

	List<LineItem> deleteById(int id);
	
	@Query("SELECT SUM(li.quantity * p.price) FROM LineItem li " +
		       "JOIN Product p ON li.productId = p.id " +
		       "WHERE li.requestId = :reqId AND li.productId IS NOT NULL")
		Double calculateTotalByRequestId(@Param("reqId") int reqId);


	
}
