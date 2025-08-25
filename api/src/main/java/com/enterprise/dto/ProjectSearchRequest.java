package com.enterprise.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ProjectSearchRequest {
    private String keyword;
    private List<Long> departmentIds;
    private List<Long> employeeIds;
    private List<String> statuses;
    private String startDate;
    private String endDate;
}
