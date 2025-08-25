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
public class DepartmentCreateDto {
    
    @JsonProperty("departmentName")
    private String departmentName;
    
    @JsonProperty("manager_id")
    private Long managerId;
    
    @JsonProperty("employee_ids")
    private List<Long> employeeIds;
    
    @JsonProperty("project_ids")
    private List<Long> projectIds;
}
