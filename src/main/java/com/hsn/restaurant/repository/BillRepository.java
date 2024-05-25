package com.hsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
	
    @Query("""
    		select b from Bill b
    		where b.user.id=:id
    		""")
	Optional<Bill> findByUserId(Long id);
    
    

}
