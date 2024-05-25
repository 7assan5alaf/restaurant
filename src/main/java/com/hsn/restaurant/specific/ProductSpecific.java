package com.hsn.restaurant.specific;

import org.springframework.data.jpa.domain.Specification;

import com.hsn.restaurant.entity.Product;

public class ProductSpecific {
	
	public static Specification<Product>withCategorey(Long id){
		return (root,query,builder)->builder.equal(root.get("categorey").get("id"),id);
	}

}
