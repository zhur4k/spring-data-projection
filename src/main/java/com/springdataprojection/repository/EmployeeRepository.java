package com.springdataprojection.repository;

import com.springdataprojection.dto.EmployeeProjection;
import com.springdataprojection.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("""
        SELECT e.firstName AS firstName,
               e.lastName AS lastName,
               e.position AS position,
               d.name AS departmentName
        FROM Employee e
        JOIN e.department d
    """)
    List<EmployeeProjection> findAllProjection();

    @Query("""
        SELECT e.firstName AS firstName,
               e.lastName AS lastName,
               e.position AS position,
               d.name AS departmentName
        FROM Employee e
        JOIN e.department d
        WHERE e.id = :id
    """)
    Optional<EmployeeProjection> findByIdProjection(Long id);
}
