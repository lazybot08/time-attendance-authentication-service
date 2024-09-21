package com.wipro.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.wipro.dto.LeaveRequest;
import com.wipro.dto.TimeOffRequest;

@FeignClient(name = "request-service", fallback = Fallback.class)
public interface RequestRestConsumer {
	@GetMapping("/api/v1/requests/time-request/manager/{managerId}")
	public ResponseEntity<List<TimeOffRequest>> getAllTimeRequestByManagerId(@RequestHeader("role") String role, @PathVariable Integer managerId);
	
	@GetMapping("/api/v1/requests/leave-request/manager/{managerId}")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequestByManagerId(@RequestHeader("role") String role, @PathVariable Integer managerId);
}
