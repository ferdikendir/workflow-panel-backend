package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.payload.ResponseUtil;
import com.ferdi.workflow_panel_backend.repository.DepartmentRepository;
import com.ferdi.workflow_panel_backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<ApiResponse<Department>> getDepartmentById(UUID id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getAllDepartments(DepartmentDto departmentDtoReq) {

        List<Department> departments = departmentRepository.findAll();
        departments.sort(Comparator.comparing(Department::getCreatedDate).reversed());

        List<DepartmentDto> departmentDtos = new ArrayList<>();

        for (Department department : departments) {
            DepartmentDto departmentDto = new DepartmentDto();

            departmentDto.setDepartmentId(department.getDepartmentId());
            departmentDto.setName(department.getName());
            departmentDto.setDescription(department.getDescription());
            departmentDto.setCreatedDate(department.getCreatedDate());
            departmentDto.setColor(department.getColor());

            departmentDtos.add(departmentDto);
        }

        return ResponseUtil.success(ResultMessage.none, departmentDtos);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(DepartmentDto departmentDto) {

        if (departmentRepository.findByName(departmentDto.getName()).isPresent()) {
            return ResponseUtil.successError(ResultMessage.sameDepartmentNameRegistered, null);
        }

        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCreatedDate(new Date());
        department.setDescription(departmentDto.getDescription());
        department.setColor(departmentDto.getColor());

        Department savedDepartment = departmentRepository.save(department);

        DepartmentDto savedDepartmentDto = new DepartmentDto();

        savedDepartmentDto.setDepartmentId(savedDepartment.getDepartmentId());
        savedDepartmentDto.setName(savedDepartment.getName());
        savedDepartmentDto.setDescription(savedDepartment.getDescription());
        savedDepartmentDto.setColor(savedDepartment.getColor());
        savedDepartmentDto.setCreatedDate(savedDepartment.getCreatedDate());

        return ResponseUtil.success(ResultMessage.createdSuccess, savedDepartmentDto);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(DepartmentDto departmentDto) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> deleteDepartment(UUID id) {

        Department department = departmentRepository.findByDepartmentId(id);

        if (department == null) {
            return ResponseUtil.successError(ResultMessage.departmentNotFound, null);
        }

        if (!department.getUsers().isEmpty()) {
            return ResponseUtil.successError(ResultMessage.departmentContainsUsers, null);
        }

        departmentRepository.delete(department);

        DepartmentDto deletedDepartmentDto = new DepartmentDto();

        deletedDepartmentDto.setDepartmentId(department.getDepartmentId());
        deletedDepartmentDto.setName(department.getName());

        return  ResponseUtil.success(ResultMessage.deleteSuccess, deletedDepartmentDto);
    }

    @Override
    public ResponseEntity<ApiResponse<DepartmentDto>> getMembers(DepartmentDto departmentDto) {
        Department department = departmentRepository.findByDepartmentId(departmentDto.getDepartmentId());

        if (department == null) {
            return ResponseUtil.successError(ResultMessage.departmentNotFound, null);
        }

        DepartmentDto departmentDto1 = new DepartmentDto();

        departmentDto1.setDepartmentId(department.getDepartmentId());
        departmentDto1.setName(department.getName());
        departmentDto1.setDescription(department.getDescription());
        departmentDto1.setColor(department.getColor());
        departmentDto1.setCreatedDate(department.getCreatedDate());

        List<User> users = department.getUsers();
        users.sort(Comparator.comparing(User::getCreatedDate).reversed());

        List<UserDto> usersDto = new ArrayList<UserDto>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setSystemUserId(user.getSystemUserId());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setCreatedDate(user.getCreatedDate());
            usersDto.add(userDto);
        }

        departmentDto1.setUsers(usersDto);

        return ResponseUtil.success(ResultMessage.none, departmentDto1);
    }
}
