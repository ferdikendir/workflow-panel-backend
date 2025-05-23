package com.ferdi.workflow_panel_backend.controller;

import com.ferdi.workflow_panel_backend.dto.AuthDto;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthDto>> login(@RequestBody UserDto userDto) {
           return authService.login(userDto.getEmail(), userDto.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@RequestBody UserDto userDto) {
        return authService.register(userDto);
    }

}
