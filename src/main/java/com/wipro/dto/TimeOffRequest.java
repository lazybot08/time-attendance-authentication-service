package com.wipro.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class TimeOffRequest {
	private Integer requestId;
	private Integer managerId;
	private Integer empId;
	private LocalDate timeOffDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDateTime requestDateTime;
	private String requestReason;
	private String status;
}
