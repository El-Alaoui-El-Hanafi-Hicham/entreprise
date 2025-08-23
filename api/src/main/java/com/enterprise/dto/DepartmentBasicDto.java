package com.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentBasicDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("department_name")
    private String departmentName;
    
    @JsonProperty("manager_name")
    private String managerName;
    
    @JsonProperty("employee_count")
    private Integer employeeCount;
}
