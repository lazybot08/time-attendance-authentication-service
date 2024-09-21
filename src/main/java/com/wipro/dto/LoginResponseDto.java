package com.wipro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponseDto {
	private Boolean success;
	private String token;
	private String message;
	private EmployeeResponseDto data;
}
