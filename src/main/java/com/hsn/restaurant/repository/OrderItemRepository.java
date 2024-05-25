package com.hsn.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.OrderItem;



@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

	@Query("""
			select o from OrderItem o where o.product.id=:itemId
			""")
	public List<OrderItem> findByProductId(Long itemId);
}
