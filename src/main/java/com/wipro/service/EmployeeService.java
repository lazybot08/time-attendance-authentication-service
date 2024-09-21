package com.wipro.service;

import java.util.List;

import com.wipro.dto.LoginResponseDto;
import com.wipro.dto.EmployeeRequestDto;
import com.wipro.dto.EmployeeResponseDto;
import com.wipro.dto.LoginRequestDto;
import com.wipro.exception.InvalidCredentialsException;
import com.wipro.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

public interface EmployeeService {
    List<EmployeeResponseDto> getAllEmployees() throws UserNotFoundException;
    List<EmployeeResponseDto> getEmployeesByManager(HttpServletRequest request) throws UserNotFoundException;
    EmployeeResponseDto getCurrentEmployee(HttpServletRequest request) throws UserNotFoundException;
    EmployeeResponseDto getEmployeeById(int theId) throws UserNotFoundException;
    String deleteEmployeeById(int theId) throws UserNotFoundException;
    LoginResponseDto createAuthenticationToken(LoginRequestDto loginRequestDto) throws UserNotFoundException, InvalidCredentialsException;
    String saveEmployee(EmployeeRequestDto employeeRequestDto) throws UserNotFoundException;
    String saveManager(EmployeeRequestDto employeeRequestDto) throws UserNotFoundException;
}
