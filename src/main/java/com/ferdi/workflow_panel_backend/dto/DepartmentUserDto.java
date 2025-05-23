package com.ferdi.workflow_panel_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentUserDto {

    private UUID departmentUserId;

    private UUID departmentId;
    private DepartmentDto department;

    private UUID systemUserId;
    private UserDto user;

    private boolean active;
    private Date createdDate;
    private Date modifiedDate;
    private String role;
}
