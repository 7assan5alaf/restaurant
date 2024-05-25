package com.hsn.restaurant.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.hsn.restaurant.entity.Code;
import com.hsn.restaurant.entity.User;
import com.hsn.restaurant.excpetion.EntityNotFound;
import com.hsn.restaurant.excpetion.OperationPermittedException;
import com.hsn.restaurant.mail.EmailService;
import com.hsn.restaurant.mail.Message;
import com.hsn.restaurant.repository.CodeRepository;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeService {

	private  final CodeRepository codeRepository;
    private final EmailService emailService;
    
	public void sendValidationCode(User user) throws MessagingException {
		Message message=new Message();
		var newToken=generateAndSaveCode(user);
	     emailService.sendEmail(user.getEmail(),message.getMessagwToUser()+"\nActivation coed is : "
		     +newToken+ "\nTo activate your account click on this link : "
					+ "http://localhost:8081/fried-chicken/auth/activate");	
	}
	public void sendValidationCodeToForgetPassword(User user) throws MessagingException {
		var newToken=generateAndSaveCode(user);
	     emailService.sendEmail(user.getEmail(),"This is OTP for you forget password  request : "
		     +newToken+ "\nTo verfiy your code click on this link : "
					+ "http://localhost:8081/fried-chicken/auth/verfiy-otp/"+user.getEmail());	
	}
	private String generateAndSaveCode(User user) {
		var generateCode=generateCodeByDigits(6);
		var code=Code.builder()
				.user(user)
				.code(generateCode)
				.createdAt(LocalDateTime.now())
				.expiredAt(LocalDateTime.now().plusMinutes(15)).build();
		
		codeRepository.save(code);
		return generateCode;
	}
	private String generateCodeByDigits(int len) {
		 final String digits ="0123456789";
		 SecureRandom random=new SecureRandom();
		 StringBuilder builder=new StringBuilder();
		for(int i=0;i<len;i++) {
			builder.append(random.nextInt(digits.length()));
		}
		 
		return builder.toString();
	}
	public Code findByCode(String code) {
		return codeRepository.findByCode(code)
				.orElseThrow(()->new EntityNotFound("no found this code"));
				
	}
	public void save(Code c) {
		codeRepository.save(c);
		
	}
	
	public Code findCodeAndUser(String code,String email) {
		return codeRepository.findCodeAndUser(code,email).orElseThrow(()-> new OperationPermittedException("Invalid OTP for email "+email));
	}
	public void deletById(String code) {
		Code otp=findByCode(code);
		codeRepository.deleteById(otp.getId());		
	}
	
}
