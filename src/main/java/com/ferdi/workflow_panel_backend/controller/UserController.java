package com.ferdi.workflow_panel_backend.controller;

import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<UserDto>>> list() {
        return  userService.list();
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserDto>> create(@RequestBody UserDto userDto) {
        return  userService.create(userDto);
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@RequestBody UserDto userDto) {
        return  userService.get(userDto);
    }

    @PostMapping("/updateDepartment")
    public ResponseEntity<ApiResponse<UserDto>> updateDepartment(@RequestBody UserDto userDto) {
        return userService.updateDepartment(userDto);
    }

}
