package com.example.demo.exception;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.utils.MessageUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class BussinessException extends Exception {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MessageUtils messageUtils;

	private String msgCode;

	private Object param[];

	public BussinessException(String msgCode, Object[] param) {
		this.msgCode = msgCode;
		this.param = param;
	}
}
