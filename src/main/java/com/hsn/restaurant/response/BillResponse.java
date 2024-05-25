package com.hsn.restaurant.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class BillResponse {
	
	private Long orderNumber;
	private String name;
	private String phoneNumber;
	private String address;
	private long amount;
	private List<String>orderDetails;
	private double totalPrice;
	
	

}
