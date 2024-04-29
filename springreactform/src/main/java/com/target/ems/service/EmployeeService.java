package com.target.ems.service;

import com.target.ems.dto.EmployeeDto;
import com.target.ems.entity.Employee;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeedto);
    EmployeeDto getEmployeeById(Long employeeId);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);
    EmployeeDto deleteEmployee(Long employeeId);
    List<EmployeeDto> deleteAllEmployees();
}
