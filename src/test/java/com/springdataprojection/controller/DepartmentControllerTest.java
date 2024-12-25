package com.springdataprojection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdataprojection.model.Department;
import com.springdataprojection.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDepartmentsSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        List<Department> Departments = Arrays.asList(department);

        when(departmentService.getAllDepartments()).thenReturn(Departments);

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Departments)));
    }

    @Test
    void getAllDepartmentsException() throws Exception {
        when(departmentService.getAllDepartments()).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isInternalServerError());

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void getDepartmentByIdSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        mockMvc.perform(get("/api/departments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(department)));
    }

    @Test
    void getDepartmentById() throws Exception {
        when(departmentService.getDepartmentById(1L)).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/Departments/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createDepartmentSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        mockMvc.perform(post("/api/departments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void createDepartmentException() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        doThrow(new RuntimeException()).when(departmentService).saveDepartment(any(Department.class));

        mockMvc.perform(post("/api/departments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isInternalServerError());

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void updateDepartmentSuccess() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        mockMvc.perform(put("/api/departments/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).updateDepartment(any(Department.class));
    }

    @Test
    void updateDepartmentException() throws Exception {
        Department department = new Department();
        department.setId(1L);
        department.setName("Department 1");

        doThrow(new RuntimeException()).when(departmentService).updateDepartment(any(Department.class));

        mockMvc.perform(put("/api/departments/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isInternalServerError());

        verify(departmentService, times(1)).updateDepartment(any(Department.class));
    }

    @Test
    void deleteDepartmentSuccess() throws Exception {
        mockMvc.perform(delete("/api/departments/delete/{id}", 1L))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }

    @Test
    void deleteDepartmentException() throws Exception {
        doThrow(RuntimeException.class).when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/Departments/delete/{id}", 1L))
                .andExpect(status().isInternalServerError());
    }
}
