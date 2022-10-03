package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class ApiResponseDto {
	
	private Boolean isError;

	private String code;
	
	private String message;
	
	private Object data;
	
	private String status;
}
