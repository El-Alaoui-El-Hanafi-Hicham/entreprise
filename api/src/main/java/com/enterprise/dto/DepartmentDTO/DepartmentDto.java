package com.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("departmentName")
    private String departmentName;
    
    @JsonProperty("manager")
    private EmployeeBasicDto manager;
    
    @JsonProperty("employees")
    private List<EmployeeBasicDto> employees;
    
    @JsonProperty("projects")
    private List<ProjectBasicDto> projects;
    
    @JsonProperty("employee_count")
    private Integer employeeCount;
    
    @JsonProperty("project_count")
    private Integer projectCount;
}
