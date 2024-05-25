package com.hsn.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsn.restaurant.request.SignUpRequest;
import com.hsn.restaurant.request.SigninRequest;
import com.hsn.restaurant.service.AuthentcationService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthentcationService authentcationService;

	
	@PostMapping("/signup")
	public ResponseEntity<?>signup(@RequestBody @Valid SignUpRequest request) throws MessagingException{
		return ResponseEntity.ok(authentcationService.signup(request));
	}
	@PutMapping("/activate")
	public ResponseEntity<?>activationAccount(@RequestParam String code) throws MessagingException{
		return ResponseEntity.ok(authentcationService.activationAccount(code));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?>signin(@Valid @RequestBody SigninRequest request){
		return ResponseEntity.ok(authentcationService.signin(request));
	}
	
	//forget-password

    @PostMapping("/forget-password")	
	public ResponseEntity<?>sendOtpToEmail(@RequestParam String email) throws MessagingException{
		return ResponseEntity.ok(authentcationService.sendOtpToEmail(email));
	}
    
    @PostMapping("/verify-otp/{email}")
    public ResponseEntity<?>verifiyOtp(@PathVariable String email,@RequestParam String otp) throws MessagingException{
    	return ResponseEntity.ok(authentcationService.verifiyOtp(email, otp));
    }
    @PutMapping("/changed-password")
    public ResponseEntity<?>changePassword(@RequestParam String email,@RequestParam String newPassword
    		,@RequestParam String confirmPassword ) throws MessagingException{
    	return ResponseEntity.ok(authentcationService.changePassword(email,newPassword,confirmPassword));
    }
}
