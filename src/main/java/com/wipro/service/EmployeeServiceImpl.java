package com.wipro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.convertors.EmployeeConvertor;
import com.wipro.dao.DepartmentRepository;
import com.wipro.dao.EmployeeRepository;
import com.wipro.dao.RoleRepository;
import com.wipro.dto.LoginResponseDto;
import com.wipro.dto.EmployeeRequestDto;
import com.wipro.dto.EmployeeResponseDto;
import com.wipro.dto.LoginRequestDto;
import com.wipro.entity.Employee;
import com.wipro.enums.RoleName;
import com.wipro.exception.InvalidCredentialsException;
import com.wipro.exception.UserNotFoundException;
import com.wipro.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> empList = employeeRepository.findAll();
        if (empList.isEmpty()) {
            throw new DataRetrievalFailureException("No employees found.");
        }
        return empList.stream().map(EmployeeConvertor::convertToDto).toList();
    }
    
	@Override
	public List<EmployeeResponseDto> getEmployeesByManager(HttpServletRequest request) throws UserNotFoundException {
		String jwtToken = jwtUtil.getToken(request);
        String email = jwtUtil.extractUsername(jwtToken);

        Employee manager = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Employee not found!"));
        List<Employee> empList = employeeRepository.findByManager(manager);
        if (empList.isEmpty()) {
			throw new DataRetrievalFailureException("No employees found.");
		}
        return empList.stream().map(EmployeeConvertor::convertToDto).toList();
	}

    @Override
    public EmployeeResponseDto getEmployeeById(int theId) {
        Employee employee = employeeRepository.findById(theId)
                .orElseThrow(() -> new UserNotFoundException("Employee not found with ID: " + theId));
        return EmployeeConvertor.convertToDto(employee);
    }
    
    @Override
    public EmployeeResponseDto getCurrentEmployee(HttpServletRequest request) {
        String jwtToken = jwtUtil.getToken(request);
        String email = jwtUtil.extractUsername(jwtToken);

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Employee not found!"));

        return EmployeeConvertor.convertToDto(employee);
    }

    @Override
    public String deleteEmployeeById(int theId) {
        if (!employeeRepository.existsById(theId)) {
            throw new UserNotFoundException("Employee not found with ID: " + theId);
        }
        employeeRepository.deleteById(theId);
        return "Deleted employee with ID: " + theId;
    }

    @Override
    public LoginResponseDto createAuthenticationToken(LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid credentials provided.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);

        Employee employee = employeeRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        EmployeeResponseDto response = EmployeeConvertor.convertToDto(employee);

        return new LoginResponseDto(true, jwt, "User logged in successfully.", response);
    }

    @Override
    public String saveEmployee(EmployeeRequestDto employeeRequestDto) {
        return saveEmployeeByRole(employeeRequestDto, RoleName.ROLE_EMPLOYEE);
    }

    @Override
    public String saveManager(EmployeeRequestDto employeeRequestDto) {
        return saveEmployeeByRole(employeeRequestDto, RoleName.ROLE_MANAGER);
    }

    private String saveEmployeeByRole(EmployeeRequestDto employeeRequestDto, RoleName roleName) {
        if (employeeRepository.findByEmail(employeeRequestDto.getEmail()).isPresent()) {
            return "Email is already taken!";
        }

        Employee employee = new Employee();
        populateEmployeeFromDto(employeeRequestDto, employee);
        employee.setRole(roleRepository.findByRoleName(roleName));
        employeeRepository.save(employee);

        return "User registered successfully";
    }

    private void populateEmployeeFromDto(EmployeeRequestDto dto, Employee employee) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setMobile(dto.getMobile());
        employee.setBirthDate(dto.getBirthDate());
        employee.setGender(dto.getGender());
        employee.setAddress(dto.getAddress());
        employee.setDesignation(dto.getDesignation());

        departmentRepository.findByDeptName(dto.getDepartment())
                .ifPresent(employee::setDepartment);

        employeeRepository.findById(dto.getManagerId())
                .ifPresent(employee::setManager);

        employee.setHireDate(dto.getHireDate());
    }
}

