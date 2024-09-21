package com.wipro.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LeaveRequest {
	private Integer requestId;
	private Integer managerId;
	private Integer empId;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime requestDateTime;
	private String leaveType;
	private String requestReason;
	private String status;
}
