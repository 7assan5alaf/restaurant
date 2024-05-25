package com.hsn.restaurant.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	private String productName;
	private String synopsis;
	private String cover;
	private String categoreyName;
	private double price;
}
