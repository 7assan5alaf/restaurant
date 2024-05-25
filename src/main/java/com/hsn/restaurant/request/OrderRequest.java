package com.hsn.restaurant.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class OrderRequest {

	@NotBlank(message="You should be enter address")
	@NotEmpty(message="You should be enter address")
	private String address;
	@NotBlank(message="You should be enter phone number")
	@NotEmpty(message="You should be enter phone number")
	private String phoneNumber;
	
}
