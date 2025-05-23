package com.ferdi.workflow_panel_backend.service;

import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    ResponseEntity<ApiResponse<UserDto>> create(UserDto userDto);
    ResponseEntity<ApiResponse<List<UserDto>>> list();
    ResponseEntity<ApiResponse<UserDto>> get(UserDto userDto);
    ResponseEntity<ApiResponse<UserDto>> update(UserDto userDto);
    ResponseEntity<ApiResponse<UserDto>> updateDepartment(UserDto userDtoFromRequest);
}
