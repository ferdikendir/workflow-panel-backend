package com.ferdi.workflow_panel_backend.service;

import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;

public interface UserService {
    User register(UserDto userDto);
    User login(String email, String password);
}
