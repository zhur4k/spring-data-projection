package com.springdataprojection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdataprojection.model.Department;
import com.springdataprojection.model.Employee;
import com.springdataprojection.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEmployeesSuccess() throws Exception {

        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setPosition("Manager");
        employee1.setSalary(BigDecimal.valueOf(1000));
        employee1.setDepartment(department);

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setPosition("Developer");
        employee2.setSalary(BigDecimal.valueOf(1200));
        employee2.setDepartment(department);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employees)));
    }

    @Test
    void getAllEmployeesException() throws Exception {

        when(employeeService.getAllEmployees()).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isInternalServerError());

        verify(employeeService,times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeByIdSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Manager");
        employee.setSalary(BigDecimal.valueOf(1000));
        employee.setDepartment(department);


        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(employee)));
    }

    @Test
    void getEmployeeById() throws Exception{
        when(employeeService.getEmployeeById(1L)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/employees/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createEmployeeSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Manager");
        employee.setSalary(BigDecimal.valueOf(1000));
        employee.setDepartment(department);

        mockMvc.perform(post("/api/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void createEmployeeException() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Manager");
        employee.setSalary(BigDecimal.valueOf(1000));
        employee.setDepartment(department);

        doThrow(new RuntimeException()).when(employeeService).addEmployee(any(Employee.class));

        mockMvc.perform(post("/api/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isInternalServerError());

        verify(employeeService, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void updateEmployeeSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Manager");
        employee.setSalary(BigDecimal.valueOf(1000));
        employee.setDepartment(department);

        mockMvc.perform(put("/api/employees/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    void updateEmployeeException() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Manager");
        employee.setSalary(BigDecimal.valueOf(1000));
        employee.setDepartment(department);

        doThrow(new RuntimeException()).when(employeeService).updateEmployee(any(Employee.class));

        mockMvc.perform(put("/api/employees/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isInternalServerError());

        verify(employeeService, times(1)).updateEmployee(any(Employee.class));
    }

    @Test
    void deleteEmployeeSuccess() throws Exception {
        mockMvc.perform(delete("/api/employees/delete/{id}", 1L))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    void deleteEmployeeException() throws Exception {

        doThrow(RuntimeException.class).when(employeeService).deleteEmployee(1L);
        mockMvc.perform(delete("/api/employees/delete/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }
}