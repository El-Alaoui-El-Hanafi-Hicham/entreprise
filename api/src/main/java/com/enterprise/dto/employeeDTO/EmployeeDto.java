package com.enterprise.dto.employeeDTO;

import com.enterprise.dto.DepartmentDTO.DepartmentBasicDto;
import com.enterprise.dto.TaskDTO.TaskBasicDto;
import com.enterprise.dto.projectDTO.ProjectBasicDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @JsonProperty("hire_date")
    private Date hireDate;
    
    @JsonProperty("job_title")
    private String jobTitle;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("department")
    private DepartmentBasicDto department;
    
    @JsonProperty("projects")
    private List<ProjectBasicDto> projects;
    
    @JsonProperty("tasks")
    private List<TaskBasicDto> tasks;
    
    @JsonProperty("full_name")
    private String fullName;
    
    @JsonProperty("project_count")
    private Integer projectCount;
    
    @JsonProperty("task_count")
    private Integer taskCount;

}
