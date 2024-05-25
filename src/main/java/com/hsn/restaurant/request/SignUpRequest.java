package com.hsn.restaurant.request;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Min;
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
public class SignUpRequest {

	@NotBlank(message="You should be enter fullname")
	@NotEmpty(message="You should be enter fullname")
	private String fullName;
	@NotBlank(message="You should be enter emial")
	@Email(message = "You should be enter vaild email")
	private String email;
	@Min(message = "You should be entre 8 characters long minimum",value = 8)
	private String password;
	@NotBlank(message = "You should be entre the same password")
	private String confirmPassword;
	@NotBlank(message="You should be enter address")
	@NotEmpty(message="You should be enter adress")
	private String address;
	@Min(message = "You should be entre 11 number long minimum and maximum",value =11)
	private String phoneNumber;
}
