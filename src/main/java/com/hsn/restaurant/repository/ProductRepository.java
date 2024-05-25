package com.hsn.restaurant.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product>{

	@Query("""
			select p from Product p
			where p.categorey.id=:categoreyId
			""")
	Page<Product>findAll(Long categoreyId,Pageable pageable);
}
