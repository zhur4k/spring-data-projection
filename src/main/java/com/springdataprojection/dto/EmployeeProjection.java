package com.springdataprojection.dto;

public interface EmployeeProjection {

    default String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    String getDepartmentName();

    String getPosition();

    String getFirstName();

    String getLastName();
}
