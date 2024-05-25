package com.hsn.restaurant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hsn.restaurant.entity.Code;


@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

	Optional<Code>findByCode(String token);

	@Query("""
			select c from Code c where c.code=:code and c.user.email=:email
			""")
	Optional<Code> findCodeAndUser(String code, String email);
}
