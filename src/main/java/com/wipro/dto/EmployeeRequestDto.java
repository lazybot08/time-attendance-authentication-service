package com.wipro.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmployeeRequestDto {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Long mobile;
	private LocalDate birthDate;
	private String gender;
	private String address;
	private String designation;
	private String department;
	private Integer managerId;
	private LocalDate hireDate;
}
