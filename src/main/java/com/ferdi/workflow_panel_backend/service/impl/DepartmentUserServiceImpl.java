package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.dto.DepartmentUserDto;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.entity.DepartmentUser;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.payload.ResponseUtil;
import com.ferdi.workflow_panel_backend.repository.DepartmentUserRepository;
import com.ferdi.workflow_panel_backend.repository.UserRepository;
import com.ferdi.workflow_panel_backend.service.DepartmentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentUserServiceImpl implements DepartmentUserService {

    @Autowired
    private DepartmentUserRepository departmentUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<List<DepartmentUserDto>>> list(UserDto userDto) {

        Optional<User> user = userRepository.findBySystemUserId(userDto.getSystemUserId());

        if (!user.isPresent()) {
            return ResponseUtil.success(ResultMessage.userNotFound, null);
        }


        List<DepartmentUser> departmentUsers = departmentUserRepository.findByUser(
                user.get(),
                Sort.by(Sort.Direction.DESC, "modifiedDate")
                );

        List<DepartmentUserDto> departmentUserDtos = new ArrayList<DepartmentUserDto>();

        for (DepartmentUser departmentUser : departmentUsers) {

            Department department = departmentUser.getDepartment();

            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setDepartmentId(department.getDepartmentId());
            departmentDto.setName(department.getName());
            departmentDto.setColor(department.getColor());

            User _user = departmentUser.getUser();

            UserDto _userDto = new UserDto();
            _userDto.setDepartment(departmentDto);
            _userDto.setSystemUserId(_user.getSystemUserId());
            _userDto.setName(_user.getName());
            _userDto.setLastName(_user.getLastName());
            _userDto.setEmail(_user.getEmail());

            DepartmentUserDto departmentUserDto = new DepartmentUserDto();

            departmentUserDto.setDepartment(departmentDto);
            departmentUserDto.setUser(_userDto);
            departmentUserDto.setActive(departmentUser.isActive());
            departmentUserDto.setCreatedDate(departmentUser.getCreatedDate());
            departmentUserDto.setModifiedDate(departmentUser.getModifiedDate());
            departmentUserDto.setRole(_user.getRole().toString());
            departmentUserDtos.add(departmentUserDto);

        }

        return ResponseUtil.success(ResultMessage.none, departmentUserDtos);
    }
}
