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
public class TaskBasicDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("task_name")
    private String taskName;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("status")
    private Boolean status;
    
    @JsonProperty("start_date")
    private Date startDate;
    
    @JsonProperty("end_date")
    private Date endDate;
    
    @JsonProperty("priority")
    private String priority;
}
