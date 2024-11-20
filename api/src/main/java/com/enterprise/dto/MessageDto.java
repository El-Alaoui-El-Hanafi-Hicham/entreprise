package com.enterprise.dto;

import com.enterprise.entity.Employee;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageDto {
    private String chat_id;
    private EmployeeDto sender;
    private EmployeeDto receiver;
    private String message;
    private Long id;
    private Boolean isRead;
    private Date date;
}
