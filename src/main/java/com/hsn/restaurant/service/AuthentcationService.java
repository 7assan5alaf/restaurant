package com.hsn.restaurant.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hsn.restaurant.entity.Cart;
import com.hsn.restaurant.entity.Code;
import com.hsn.restaurant.entity.User;
import com.hsn.restaurant.excpetion.EntityNotFound;

import com.hsn.restaurant.repository.CartRepository;
import com.hsn.restaurant.repository.UserRepository;
import com.hsn.restaurant.request.SignUpRequest;
import com.hsn.restaurant.request.SigninRequest;
import com.hsn.restaurant.response.LoginResponse;
import com.hsn.restaurant.security.JwtService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthentcationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final CodeService codeService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final CartRepository cartRepository;
	public String signup(SignUpRequest request) throws MessagingException {	
		if(!request.getPassword().endsWith(request.getConfirmPassword()))
			return "don't match password you should be enter the same password";
		
		var user=User.builder()
				.email(request.getEmail())
				.fullName(request.getFullName())
				.role("USER")
				.password(passwordEncoder.encode(request.getPassword()))
				.phoneNumber(request.getPhoneNumber())
				.enable(false)
				.address(request.getAddress())
				.build();
		var cart=Cart.builder()
				.customer(user)
				.createdBy(user.getEmail())
				.build();
		userRepository.save(user);
		cartRepository.save(cart);
		codeService.sendValidationCode(user);
		return "Registration successfully.We send mail to you to activation your account";
	}
	
	public String activationAccount(String code) throws MessagingException {
		Code c=codeService.findByCode(code);
		if(c.getUser().isEnable())return "Your account is activation";
		if(LocalDateTime.now().isAfter(c.getExpiredAt())) {
			codeService.sendValidationCode(c.getUser());
			codeService.deletById(code);
			return "Activation code has expired, A new code has been sent to the same email address";
		}
		if(c.getCode().equals(code)) {
			c.setValidatedAt(LocalDateTime.now());
			c.getUser().setEnable(true);
			userRepository.save(c.getUser());
			codeService.save(c);
			return "Activation your account successfully";
		}
		return "should be enter vaild code";
	}

	public LoginResponse signin(SigninRequest request) {
		var auth=authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user=(User)auth.getPrincipal();
		var claims=new HashMap<String,Object>();
		claims.put("fullName", user.getFullName());
		var token=jwtService.generateToken(claims,user);
		return LoginResponse.builder().token(token).build();
	}

	//forget-password
	public String sendOtpToEmail(String email) throws MessagingException {
		var user=userRepository.findByEmail(email).orElseThrow(()->new EntityNotFound("User not found"));
		codeService.sendValidationCodeToForgetPassword(user);
		return "Email send to verification";
	}
	
	public String verifiyOtp(String email,String code) throws MessagingException {
		var user=userRepository.findByEmail(email).orElseThrow(()->new EntityNotFound("User not found"));
		
		Code otp=codeService.findCodeAndUser(code, email);
		if(LocalDateTime.now().isAfter(otp.getExpiredAt())) {
			codeService.deletById(otp.getCode());
			codeService.sendValidationCodeToForgetPassword(user);
			return "Verification OTP has expired, A new OTP has been sent to the same email address";
		}
		otp.setValidatedAt(LocalDateTime.now());
		return "OTP verified!";
	}
	
	public String changePassword(String email,String newPasswoed,String confirmPassword) {
		var user=userRepository.findByEmail(email).orElseThrow(()->new EntityNotFound("User not found"));
		
		if(!Objects.equals(newPasswoed, confirmPassword)){
			return "You should enter the same password";
		}
		
		user.setPassword(passwordEncoder.encode(confirmPassword));
		userRepository.save(user);
		return "changed password successfully";
	}
	
}
