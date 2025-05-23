package com.ferdi.workflow_panel_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthDto {
    private UserDto user;
    private String token;
    private String refreshToken;
}
