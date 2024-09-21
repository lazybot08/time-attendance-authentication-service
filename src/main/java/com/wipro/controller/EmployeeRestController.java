package com.wipro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.LoginResponseDto;
import com.wipro.dto.EmployeeRequestDto;
import com.wipro.dto.EmployeeResponseDto;
import com.wipro.dto.LoginRequestDto;
import com.wipro.exception.InvalidCredentialsException;
import com.wipro.exception.UserNotFoundException;
import com.wipro.feign.TimeRestConsumer;
import com.wipro.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TimeRestConsumer consumer;

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        } catch (UserNotFoundException e) {
        	throw e;
        } catch(Exception e) {
        	throw e;
        }
    }
    
    @GetMapping("/employees-by-manager")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByManager(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeesByManager(request));
        } catch (UserNotFoundException e) {
        	throw e;
        } catch(Exception e) {
        	throw e;
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<EmployeeResponseDto> getCurrentEmployee(HttpServletRequest request) {
        try {
            return ResponseEntity.ok(employeeService.getCurrentEmployee(request));
        } catch (UserNotFoundException e) {
        	throw e;
        } catch(Exception e) {
        	throw e;
        }
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable int employeeId) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
        } catch (UserNotFoundException e) {
        	throw e;
        } catch(Exception e) {
        	throw e;
        }
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int employeeId) {
        try {
            return ResponseEntity.ok(employeeService.deleteEmployeeById(employeeId));
        } catch (UserNotFoundException e) {
        	throw e;
        } catch(Exception e) {
        	throw e;
        }
    }

    @PostMapping("/register-employee")
    public ResponseEntity<String> registerEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        try {
            return ResponseEntity.ok(employeeService.saveEmployee(employeeRequestDto));
        } catch (UserNotFoundException e) {
        	throw e;
        } catch(Exception e) {
        	throw e;
        }
    }

    @PostMapping("/register-manager")
    public ResponseEntity<String> registerManager(@RequestBody EmployeeRequestDto employeeRequestDto) {
        try {
            return ResponseEntity.ok(employeeService.saveManager(employeeRequestDto));
        } catch (UserNotFoundException e) {
            throw e;
        } catch(Exception e) {
        	throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) {
        try {
        	LoginResponseDto response = employeeService.createAuthenticationToken(loginRequestDto);
        	consumer.clockIn(response.getData().getRole(), response.getData().getEmpId());
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException | UserNotFoundException e) {
            throw e;
        } catch(Exception e) {
        	throw e;
        }
    }
}
