package com.ferdi.workflow_panel_backend.service;

import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponse<User>> register(UserDto userDto);
    ResponseEntity<ApiResponse<User>> login(String email, String password);
    ResponseEntity<ApiResponse<User>> updatePassword(UserDto userDto);
}
