package com.wipro.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.dto.AttendanceReportDto;
import com.wipro.dto.AttendanceResponseDto;
import com.wipro.dto.EmployeeResponseDto;
import com.wipro.feign.TimeRestConsumer;
import com.wipro.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
	
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private TimeRestConsumer timeConsumer;
	
    @GetMapping("/three-months")
    public ResponseEntity<List<AttendanceReportDto>> getAttendanceOfLastThreeMonths(HttpServletRequest request) {
        return getAttendanceReport(request, LocalDate.now().minusMonths(3).minusDays(1), LocalDate.now().minusDays(1));
    }
	
    @GetMapping("/month")
    public ResponseEntity<List<AttendanceReportDto>> getAttendanceOfLastMonth(HttpServletRequest request) {
        return getAttendanceReport(request, LocalDate.now().minusMonths(1).minusDays(1), LocalDate.now().minusDays(1));
    }
	
    @GetMapping("/week")
    public ResponseEntity<List<AttendanceReportDto>> getAttendanceOfLastWeek(HttpServletRequest request) {
        return getAttendanceReport(request, LocalDate.now().minusWeeks(1).minusDays(1), LocalDate.now().minusDays(1));
    }

    private ResponseEntity<List<AttendanceReportDto>> getAttendanceReport(HttpServletRequest request, LocalDate startDate, LocalDate endDate) {
        List<EmployeeResponseDto> list = employeeService.getEmployeesByManager(request);
        List<AttendanceReportDto> responseList = new ArrayList<>();

        for (EmployeeResponseDto emp : list) {
            AttendanceResponseDto report = timeConsumer.generateAttendanceReport("ROLE_MANAGER", emp.getEmpId(), startDate, endDate).getBody();
            AttendanceReportDto response = new AttendanceReportDto();
            response.setEmpId(emp.getEmpId());
            response.setEmpName(emp.getFirstName() + " " + emp.getLastName());
            response.setTotalWorkHours(report.getTotalWorkHours());
            response.setTotalNetHours(report.getTotalNetHours());
            response.setTotalOvertimeHours(report.getTotalOvertimeHours());
            responseList.add(response);
        }
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
