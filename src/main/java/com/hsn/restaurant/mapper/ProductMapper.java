package com.hsn.restaurant.mapper;

import org.springframework.stereotype.Service;

import com.hsn.restaurant.entity.Product;
import com.hsn.restaurant.request.ProductRequest;
import com.hsn.restaurant.response.ProductResponse;
import com.hsn.restaurant.service.ProductService;


@Service
public class ProductMapper {
	
	public ProductResponse toProductResponse(Product product) {
		return ProductResponse
				.builder()
				.productName(product.getName())
				.cover(ProductService.readFileFromLocatin(product.getCover()))
				.synopsis(product.getSynopsis())
				.status(true)
				.price(product.getPrice())
				.categoreyName(product.getCategorey().getName())
				.build();
	}

	public ProductResponse toUpdateProduct(ProductRequest request) {
		 return ProductResponse
				 .builder()
				 .cover(ProductService.readFileFromLocatin(request.getCover()))
				 .categoreyName(request.getCategoreyName())
				 .price(request.getPrice())
				 .productName(request.getProductName())
				 .synopsis(request.getSynopsis())
				 .build();
		
	}
}
