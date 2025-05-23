package com.ferdi.workflow_panel_backend.controller;

import com.ferdi.workflow_panel_backend.dto.DepartmentUserDto;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.DepartmentUser;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.service.DepartmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departmentUser")
@CrossOrigin(origins = "*")
public class DepartmentUserController {

    @Autowired
    private DepartmentUserService departmentUserService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<DepartmentUserDto>>> post(@RequestBody UserDto userDto) {
        return  departmentUserService.list(userDto);
    }

}
