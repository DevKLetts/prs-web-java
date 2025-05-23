package com.prs.db;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Request;

public interface RequestRepo extends JpaRepository<Request, Integer> { 

		Optional<Request> findById(int id);

		List<Request> findAll();

		List<Request> save(List<Request> requests);

		//List<Request> update(Request request);

		List<Request> deleteById(int id);
}


