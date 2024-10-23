package com.enterprise.dto;

import com.enterprise.entity.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class EmployeeDto {

    @JsonProperty("id")
    public  Long id;
    @JsonProperty("first_name")
    public String first_name;
    @JsonProperty("last_name")
    public String last_name;
    @JsonProperty("email")
    public String email;
    @JsonProperty("department_id")
    public Long department_id;

}
