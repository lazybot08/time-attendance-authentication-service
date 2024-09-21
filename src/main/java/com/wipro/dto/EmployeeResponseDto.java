package com.wipro.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponseDto {

    private Integer empId;
    private String firstName;
    private String lastName;
    private String email;
    private Long mobile;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private String designation;
    private String role;
    private LocalDate hireDate;
    private String department;
    private EmployeeResponseDto manager;
    
}
