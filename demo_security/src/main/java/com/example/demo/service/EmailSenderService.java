package com.example.demo.service;

public interface EmailSenderService {

	void sendEmail(String toEmail, String subject, String content);
}
