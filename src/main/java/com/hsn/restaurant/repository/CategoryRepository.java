package com.hsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.Categorey;

@Repository
public interface CategoryRepository extends JpaRepository<Categorey,Long>{
	
	Optional<Categorey>findByName(String name);

}
