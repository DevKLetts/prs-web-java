package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> { 

		Optional<Request> findById(int id);
		
		
		@Query("SELECT COALESCE(MAX(SUBSTRING(r.requestNumber, 8, 4)), '0000') FROM Request r " +
			       "WHERE SUBSTRING(r.requestNumber, 2, 6) = :today")
		String findMaxRequestSuffix(@Param("today") String today);

		List<Request> findAll();

		//List<Request> findByUserIdAndStatus(int userId, String status);
		
		List<Request> findByStatus(String status);
		
		List<Request> save(List<Request> requests);

		//List<Request> update(Request request);

		List<Request> deleteById(int id);
}


