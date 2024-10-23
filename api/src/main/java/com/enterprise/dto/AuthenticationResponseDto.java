package com.enterprise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor

@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {
    @JsonProperty("JwtKey")
    public String JwtKey;
    @JsonProperty("Message")
    public  String Message;
    @JsonProperty("Status")
    public boolean Status;

}
