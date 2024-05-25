package com.hsn.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.Cart;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("""
			select c from Cart c where c.customer.id=:id
			""" )
	Optional<Cart> findByCustomerId(Long id);
}
