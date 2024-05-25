package com.hsn.restaurant.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	
	private byte[] cover;
	private String productName;
	private String synopsis;
	private double price;
	private boolean status;
	private String categoreyName;
	

}
