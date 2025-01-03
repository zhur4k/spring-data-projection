package com.springdataprojection.service.impl;

import com.springdataprojection.dto.EmployeeProjection;
import com.springdataprojection.model.Employee;
import com.springdataprojection.repository.EmployeeRepository;
import com.springdataprojection.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<EmployeeProjection> getAllEmployeesProjection() {
        return employeeRepository.findAllProjection();
    }

    @Override
    public EmployeeProjection getEmployeeProjectionById(Long id) {
        return employeeRepository.findByIdProjection(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
