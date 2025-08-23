package com.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBasicDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("project_name")
    private String projectName;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("start_date")
    private Date startDate;
    
    @JsonProperty("end_date")
    private Date endDate;
}
