package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	private final String SENDER = "chenshiye1005@gmail.com";

	@Override
	public void sendEmail(String toEmail, String subject, String content) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(this.SENDER);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(content);

		mailSender.send(mailMessage);
		System.out.println("Send mail successfully.");
	}

}
