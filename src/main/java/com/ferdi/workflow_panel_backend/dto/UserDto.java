package com.ferdi.workflow_panel_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private UUID systemUserId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Date createdDate;

    private UUID departmentId;
    private DepartmentDto department;
}