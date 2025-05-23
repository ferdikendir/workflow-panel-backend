package com.ferdi.workflow_panel_backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDto {
    private UUID departmentId;
    private String name;
    private String description;
    private String color;
    private Date createdDate;

    private List<UserDto> users;
}
