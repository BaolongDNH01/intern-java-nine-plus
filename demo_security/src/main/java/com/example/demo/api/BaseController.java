package com.example.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ApiResponseDto;
import com.example.demo.utils.CommonConstants;
import com.example.demo.utils.MessageUtils;

public class BaseController {
	@Autowired
	MessageUtils messageUtils;

	public ResponseEntity<? extends Object> success(String msgCode, Object data, Object[] params) {
		ApiResponseDto apiResponseDto = ApiResponseDto.builder().isError(false).code(msgCode)
				.message(messageUtils.getMessage(msgCode, params)).data(data)
				.status(CommonConstants.ApiStatus.STATUS_OK).build();

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}

	public ResponseEntity<? extends Object> failed(String msgCode, Object[] params) {
		ApiResponseDto apiResponseDto = ApiResponseDto.builder().isError(true).code(msgCode)
				.message(messageUtils.getMessage(msgCode, params)).data(params)
				.status(CommonConstants.ApiStatus.STATUS_ERROR).build();

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}
}
