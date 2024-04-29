package com.target.ems.controller;

import com.target.ems.dto.EmployeeDto;
import com.target.ems.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")  // this contains the base URL
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //Add an employee
    @PostMapping(value = "/add-user")
    public ResponseEntity<EmployeeDto> createEmployee( @RequestBody EmployeeDto employeeDto)
    {
        EmployeeDto savedEmployee= employeeService.createEmployee(employeeDto);
        return new  ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Get all employees
    @GetMapping(value="/search-by-id")
    public ResponseEntity<EmployeeDto> getEmployeeById( @RequestParam(name = "eId") Long employeeId)
    {
        EmployeeDto savedEmployee= employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(savedEmployee);
    }

    //Get all employees
    @GetMapping(value= "/get-all-employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees()
    {
        List<EmployeeDto> listOfEmployees= employeeService.getAllEmployees();
        return ResponseEntity.ok(listOfEmployees);
    }

    // Update Employee Details
    @PutMapping(value = "/update-employee")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestParam(name="eId") Long employeeId, @RequestBody EmployeeDto employeeDto)
    {
        EmployeeDto savedEmployee= employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok(savedEmployee);
    }


    //delete employee by Id
    @GetMapping(value = "/delete-employee")
    public ResponseEntity<EmployeeDto> deleteEmployee(@RequestParam(name="eId") Long employeeId)
    {
        EmployeeDto employeeDto= employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(employeeDto);
    }

    // delete all employees
    @GetMapping(value="/delete-all-employees")
    public ResponseEntity<List<EmployeeDto>> deleteAllEmployees()
    {
        List<EmployeeDto>listOfDeletedEmployees=employeeService.deleteAllEmployees();
        return ResponseEntity.ok(listOfDeletedEmployees);
    }

}
