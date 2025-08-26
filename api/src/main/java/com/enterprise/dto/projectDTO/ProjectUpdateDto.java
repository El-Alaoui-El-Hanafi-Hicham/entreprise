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
public class ProjectUpdateDto {
    
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
    
    @JsonProperty("manager_id")
    private Long managerId;
    
    @JsonProperty("owner_id")
    private Long ownerId;
    
    @JsonProperty("employee_ids")
    private List<Long> employeeIds;
    
    @JsonProperty("department_ids")
    private List<Long> departmentIds;
}
