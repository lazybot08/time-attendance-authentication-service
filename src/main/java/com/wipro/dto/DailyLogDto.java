package com.wipro.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyLogDto {
    private LocalDate workDate;
    private LocalTime clockInTime;
    private LocalTime clockOutTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private Double workHours;
    private Double netHours;
    private Double overtimeHours;
}
