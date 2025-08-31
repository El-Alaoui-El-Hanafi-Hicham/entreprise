package com.enterprise.dto.TaskDTO;

import com.enterprise.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    private String status;
    
    @JsonProperty("start_date")
    private LocalDate startDate;
    
    @JsonProperty("end_date")
    private LocalDate endDate;
    
    @JsonProperty("priority")
    private String priority;

    @JsonProperty("employees")
    private List<Employee> employees;

    public Collection<Long> getEmployeesIds() {
    }
}
