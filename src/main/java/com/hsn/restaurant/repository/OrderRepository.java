package com.hsn.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	
    @Query("""
    		select o from Order o
    		where o.user.id=:userId
    		""")
	List<Order> findByUserId(Long userId);
}
