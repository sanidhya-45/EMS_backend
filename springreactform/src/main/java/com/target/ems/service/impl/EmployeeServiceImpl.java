package com.target.ems.service.impl;

import com.target.ems.dto.EmployeeDto;
import com.target.ems.entity.Employee;
import com.target.ems.exception.ResourceNotFoundException;
import com.target.ems.mapper.EmployeeMapper;
import com.target.ems.repository.EmployeeRepository;
import com.target.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeedto) {
        Employee employee= EmployeeMapper.mapToEmployee(employeedto);

        // saving the newly created employee in repository
        // below function on saving returns an instance back
        Employee savedEmployee= employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {


        Employee savedEmployee = employeeRepository.findById(employeeId)
                .orElseThrow( ()-> new ResourceNotFoundException("Id " +employeeId+ " does not exists"));

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> savedEmployee= employeeRepository.findAll();

        return savedEmployee.stream().map((e)->EmployeeMapper.mapToEmployeeDto(e)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto newDetailsEmployee) {

            Employee updatedEmployee= employeeRepository.findById(employeeId)
                    .orElseThrow( ()-> new ResourceNotFoundException("Id" + employeeId + " does not exists"));
            updatedEmployee.setFirstName(newDetailsEmployee.getFirstName());
            updatedEmployee.setLastName(newDetailsEmployee.getLastName());
            updatedEmployee.setEmail(newDetailsEmployee.getEmail());
        return EmployeeMapper.mapToEmployeeDto(employeeRepository.save(updatedEmployee));
    }

    @Override
    public EmployeeDto deleteEmployee(Long employeeId) {
        Employee savedEmployee= employeeRepository.findById(employeeId)
                .orElseThrow( ()-> new ResourceNotFoundException("Id" + employeeId + " does not exists"));
        employeeRepository.deleteById(employeeId);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public List<EmployeeDto> deleteAllEmployees() {
        List<Employee> savedEmployee= employeeRepository.findAll();
        employeeRepository.deleteAll();
        return savedEmployee.stream().map((e)->EmployeeMapper.mapToEmployeeDto(e)).collect(Collectors.toList());
    }


}
