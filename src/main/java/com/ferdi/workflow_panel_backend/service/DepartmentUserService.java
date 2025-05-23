package com.ferdi.workflow_panel_backend.service;

import com.ferdi.workflow_panel_backend.dto.DepartmentUserDto;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.DepartmentUser;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DepartmentUserService {
    public ResponseEntity<ApiResponse<List<DepartmentUserDto>>> list(UserDto userDto);
}
