package com.hsn.restaurant.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class SigninRequest {

	@NotBlank(message="You should be enter email")
	@Email(message = "You should be enter vaild email")
	private String email;
	@NotBlank(message="You should be enter password")
	private String password;
}
