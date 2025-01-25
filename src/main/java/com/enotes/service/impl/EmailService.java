package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.enotes.dto.EmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	@Autowired
	private  JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String sendFrom;
	
	public void sendEmail(EmailRequest emailRequest) throws Exception {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(sendFrom, emailRequest.getTitle());
		helper.setTo(emailRequest.getTo());
		helper.setSubject(emailRequest.getSubject());
		helper.setText(emailRequest.getMessage(), true);
		
		mailSender.send(message);
	}
}
