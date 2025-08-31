package com.enterprise.dto.TaskDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateDTO {
@JsonProperty("task_name")
    private String taskName;
@JsonProperty("description")
    private String description;
@JsonProperty("priority")
    private String priority;
@JsonProperty("status")
    private String status;
@JsonProperty("startDate")
    private  LocalDate startDate;
@JsonProperty("endDate")
    private LocalDate endDate;
@JsonProperty("employeesIds")
    private List<Long> employeesIds;
@JsonProperty("projectId")
    private Long projectId;
    @JsonProperty("managerId")
    private Long managerId;
}
