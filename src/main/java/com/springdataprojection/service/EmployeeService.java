package com.springdataprojection.service;

import com.springdataprojection.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);
}
