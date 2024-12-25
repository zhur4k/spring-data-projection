package com.springdataprojection.service;

import com.springdataprojection.model.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department saveDepartment(Department department);

    Department updateDepartment(Department department);

    void deleteDepartment(Long id);
}
