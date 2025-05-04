package com.ferdi.workflow_panel_backend.service;

import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    ResponseEntity<ApiResponse<User>> register(UserDto userDto);
    Optional<User> get(UUID systemUserId);
}
