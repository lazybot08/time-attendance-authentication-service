package com.wipro.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceResponseDto {
    private Integer empId;
    private List<DailyLogDto> dailyLogs = new ArrayList<>();
    private Double totalWorkHours = 0.0;
    private Double totalNetHours = 0.0;
    private Double totalOvertimeHours = 0.0;
}
