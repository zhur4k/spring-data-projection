package com.springdataprojection.service;

import com.springdataprojection.dto.EmployeeProjection;
import com.springdataprojection.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    List<EmployeeProjection> getAllEmployeesProjection();

    EmployeeProjection getEmployeeProjectionById(Long id);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);
}
