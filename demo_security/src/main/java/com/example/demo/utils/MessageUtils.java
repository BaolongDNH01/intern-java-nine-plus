package com.example.demo.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
	@Autowired
	private MessageSource resources;

	public String getMessage(String code, @Nullable Object[] args) {
		return resources.getMessage(code, args, Locale.ENGLISH);
	}
}
