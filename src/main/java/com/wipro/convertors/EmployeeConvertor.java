package com.wipro.convertors;

import com.wipro.dto.EmployeeResponseDto;
import com.wipro.entity.Employee;

public class EmployeeConvertor {

    public static EmployeeResponseDto convertToDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setEmpId(employee.getEmpId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setEmail(employee.getEmail());
        dto.setMobile(employee.getMobile());
        dto.setBirthDate(employee.getBirthDate());
        dto.setGender(employee.getGender());
        dto.setAddress(employee.getAddress());
        dto.setDesignation(employee.getDesignation());
        dto.setRole(employee.getRole().getRoleName());
        dto.setHireDate(employee.getHireDate());

        if (employee.getDepartment() != null) {
            dto.setDepartment(employee.getDepartment().getDeptName());
        }

        if (employee.getManager() != null) {
            Employee manager = employee.getManager();
            EmployeeResponseDto managerDto = new EmployeeResponseDto();
            managerDto.setEmpId(manager.getEmpId());
            managerDto.setFirstName(manager.getFirstName());
            managerDto.setLastName(manager.getLastName());
            managerDto.setEmail(manager.getEmail());
            dto.setManager(managerDto);
        }

        return dto;
    }
}
