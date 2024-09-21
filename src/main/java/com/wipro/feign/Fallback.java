package com.wipro.feign;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.wipro.dto.AttendanceResponseDto;
import com.wipro.dto.LeaveRequest;
import com.wipro.dto.TimeOffRequest;

@Component
public class Fallback implements TimeRestConsumer, RequestRestConsumer{

	@Override
	public ResponseEntity<Void> clockIn(String role, Integer empId){
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<AttendanceResponseDto> generateAttendanceReport(String role, Integer empId, LocalDate startDate,
			LocalDate endDate) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<List<TimeOffRequest>> getAllTimeRequestByManagerId(String role, Integer managerId) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<List<LeaveRequest>> getAllLeaveRequestByManagerId(String role, Integer managerId) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
	}

}
