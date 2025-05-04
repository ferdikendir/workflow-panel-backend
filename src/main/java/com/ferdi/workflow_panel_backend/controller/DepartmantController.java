package com.ferdi.workflow_panel_backend.controller;

import com.ferdi.workflow_panel_backend.dto.DepartmantDto;
import com.ferdi.workflow_panel_backend.entity.Departmant;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.service.DepartmantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departmant")
@CrossOrigin(origins = "*")
public class DepartmantController {

   @Autowired
   private DepartmantService departmantService;

   @PostMapping("/list")
    public ResponseEntity<ApiResponse<List<Departmant>>> listDepartmant() {
       return departmantService.getAllDepartmants();
   }

   @PostMapping("/create")
   public ResponseEntity<ApiResponse<Departmant>> createDepartmant(@RequestBody DepartmantDto departmantDto) {
      return  departmantService.createDeparmant(departmantDto);
   }
}
