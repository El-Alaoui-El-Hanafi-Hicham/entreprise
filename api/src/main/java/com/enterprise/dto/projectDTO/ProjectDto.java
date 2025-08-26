package com.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("project_name")
    private String projectName;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("start_date")
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    private LocalDate endDate;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("manager")
    private EmployeeBasicDto manager;
    
    @JsonProperty("owner")
    private EmployeeBasicDto owner;
    
    @JsonProperty("employees")
    private List<EmployeeBasicDto> employees;
    
    @JsonProperty("departments")
    private List<DepartmentBasicDto> departments;
    
    @JsonProperty("tasks")
    private List<TaskBasicDto> tasks;
    
    @JsonProperty("employee_count")
    private Integer employeeCount;
    
    @JsonProperty("department_count")
    private Integer departmentCount;
    
    @JsonProperty("task_count")
    private Integer taskCount;
}
