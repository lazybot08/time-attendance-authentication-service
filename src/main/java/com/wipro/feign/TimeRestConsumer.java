package com.wipro.feign;

import java.time.LocalDate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.wipro.dto.AttendanceResponseDto;

@FeignClient(name = "time-tracking-service", fallback = Fallback.class)
public interface TimeRestConsumer {
	
	@PostMapping("/api/v1/entry/clock-in")
	public ResponseEntity<Void> clockIn(@RequestHeader("role") String role, @RequestParam("empId") Integer empId);
	
	@GetMapping("/api/v1/entry/report")
	public ResponseEntity<AttendanceResponseDto> generateAttendanceReport(@RequestHeader("role") String role, @RequestParam Integer empId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate);
}
