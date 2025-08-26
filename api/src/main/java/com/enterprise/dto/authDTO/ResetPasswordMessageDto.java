package com.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@NoArgsConstructor
@Builder
public class ResetPasswordMessageDto {

    @JsonProperty("Message")
    public  String Message;
    @JsonProperty("Status")
    public boolean Status;
}
