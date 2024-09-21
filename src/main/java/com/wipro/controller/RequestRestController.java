package com.wipro.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.EmployeeResponseDto;
import com.wipro.dto.LeaveRequest;
import com.wipro.dto.LeaveRequestDto;
import com.wipro.dto.TimeOffRequest;
import com.wipro.dto.TimeOffRequestDto;
import com.wipro.feign.RequestRestConsumer;
import com.wipro.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/request")
public class RequestRestController {
	
	@Autowired
	private RequestRestConsumer requestRestConsumer;
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/time-requests")
	 public ResponseEntity<List<TimeOffRequestDto>> getTimeOffRequestsWithEmployeeNames(HttpServletRequest request) {
        List<EmployeeResponseDto> employeeList = employeeService.getEmployeesByManager(request);
        
        Map<Integer, String> empNameMap = employeeList.stream()
            .collect(Collectors.toMap(
                EmployeeResponseDto::getEmpId,
                emp -> emp.getFirstName() + " " + emp.getLastName()
            ));

        List<TimeOffRequest> timeOffRequests = requestRestConsumer.getAllTimeRequestByManagerId(
            request.getHeader("role"),
            Integer.parseInt(request.getHeader("empId"))
        ).getBody();

        List<TimeOffRequestDto> responseList = timeOffRequests.stream()
            .map(timeOffRequest -> {
                TimeOffRequestDto dto = new TimeOffRequestDto();
                dto.setRequestId(timeOffRequest.getRequestId());
                dto.setEmpId(timeOffRequest.getEmpId());
                dto.setManagerId(timeOffRequest.getManagerId());
                dto.setTimeOffDate(timeOffRequest.getTimeOffDate());
                dto.setStartTime(timeOffRequest.getStartTime());
                dto.setEndTime(timeOffRequest.getEndTime());
                dto.setRequestDateTime(timeOffRequest.getRequestDateTime());
                dto.setRequestReason(timeOffRequest.getRequestReason());
                dto.setStatus(timeOffRequest.getStatus());
                dto.setEmpName(empNameMap.get(timeOffRequest.getEmpId()));
                return dto;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
	
	@GetMapping("/leave-requests")
	public ResponseEntity<List<LeaveRequestDto>> getLeaveRequestsWithEmployeeNames(HttpServletRequest request){
		List<EmployeeResponseDto> employeeList = employeeService.getEmployeesByManager(request);
		Map<Integer, String> empNameMap = employeeList.stream()
	            .collect(Collectors.toMap(
	                EmployeeResponseDto::getEmpId,
	                emp -> emp.getFirstName() + " " + emp.getLastName()
	            ));
		List<LeaveRequest> leaveRequests = requestRestConsumer.getAllLeaveRequestByManagerId(
	            request.getHeader("role"),
	            Integer.parseInt(request.getHeader("empId"))
	        ).getBody();
		List<LeaveRequestDto> responseList = leaveRequests.stream()
	            .map(leaveRequest -> {
	                LeaveRequestDto dto = new LeaveRequestDto();
	                dto.setRequestId(leaveRequest.getRequestId());
	                dto.setEmpId(leaveRequest.getEmpId());
	                dto.setManagerId(leaveRequest.getManagerId());
	                dto.setStartDate(leaveRequest.getStartDate());
	                dto.setEndDate(leaveRequest.getEndDate());
	                dto.setRequestDateTime(leaveRequest.getRequestDateTime());
	                dto.setLeaveType(leaveRequest.getLeaveType());
	                dto.setRequestReason(leaveRequest.getRequestReason());
	                dto.setStatus(leaveRequest.getStatus());
	                dto.setEmpName(empNameMap.get(leaveRequest.getEmpId()));
	                return dto;
	            })
	            .collect(Collectors.toList());
		return ResponseEntity.ok(responseList);
	}
}
