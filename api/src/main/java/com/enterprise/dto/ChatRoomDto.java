package com.enterprise.dto;

import com.enterprise.entity.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields
public class ChatRoomDto {
    private String id;
    private Employee sender;
    private Employee recipient;
}
