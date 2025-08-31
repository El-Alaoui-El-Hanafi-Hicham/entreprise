package com.enterprise.dto.employeeDTO;

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
public class EmployeeCreateDto {
    
    @JsonProperty("first_name")
    private String firstName;
    
    @JsonProperty("last_name")
    private String lastName;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
    private String password;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @JsonProperty("hire_date")
    private Date hireDate;
    
    @JsonProperty("job_title")
    private String jobTitle;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("department_id")
    private Long departmentId;
    
    @JsonProperty("project_ids")
    private List<Long> projectIds;
}
