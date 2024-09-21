package com.wipro.dto;

import lombok.Data;

@Data
public class AttendanceReportDto {
	private Integer empId;
	private String empName;
	private Double totalWorkHours;
	private Double totalNetHours;
	private Double totalOvertimeHours;
}
