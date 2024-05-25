package com.hsn.restaurant.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;
	
	public void sendEmail(String to ,String body) throws MessagingException {
		Message m=new Message();
		
		MimeMessage message=javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper=new MimeMessageHelper(message,true);
		helper.setFrom(m.getFrom());
		helper.setText(body);
		helper.setSubject(m.getSubject());
		helper.setTo(to);
		javaMailSender.send(message);
	}
	
}
