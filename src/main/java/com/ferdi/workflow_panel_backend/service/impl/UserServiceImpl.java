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
import com.ferdi.workflow_panel_backend.repository.DepartmentRepository;
import com.ferdi.workflow_panel_backend.repository.DepartmentUserRepository;
import com.ferdi.workflow_panel_backend.repository.UserRepository;
import com.ferdi.workflow_panel_backend.security.JwtUtil;
import com.ferdi.workflow_panel_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentUserRepository departmentUserRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ApiResponse<UserDto>> create(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseUtil.successError(ResultMessage.sameEmailRegistered, null);
        }

        Department department = departmentRepository.findByDepartmentId(userDto.getDepartmentId());

        if (department == null) {
            return ResponseUtil.successError(ResultMessage.departmentNotFound, null);
        }

        User user = new User();
        user.setName(userDto.getName().toUpperCase(Locale.ROOT));
        user.setLastName(userDto.getLastName().toUpperCase(Locale.ROOT));
        user.setEmail(userDto.getEmail());
        // user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(User.Role.EMPLOYEE);
        user.setDepartment(department);
        user.setCreatedDate(new Date());
        User savedUser = userRepository.save(user);

        UserDto savedUserDto = new UserDto();

        savedUserDto.setSystemUserId(savedUser.getSystemUserId());
        savedUserDto.setEmail(savedUser.getEmail());
        savedUserDto.setName(savedUser.getName());
        savedUserDto.setLastName(savedUser.getLastName());
        savedUserDto.setDepartmentId(savedUser.getDepartment().getDepartmentId());
        savedUserDto.setCreatedDate(savedUser.getCreatedDate());

        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setDepartmentId(savedUser.getDepartment().getDepartmentId());
        departmentDto.setName(savedUser.getDepartment().getName());

        savedUserDto.setDepartment(departmentDto);

        addDepartmentUser(savedUser, userDto);

        return ResponseUtil.success(ResultMessage.createdSuccess, savedUserDto);
    }

    @Override
    public ResponseEntity<ApiResponse<List<UserDto>>> list() {
        List<User> users = userRepository.findAll();
        users.sort(Comparator.comparing(User::getCreatedDate).reversed());

        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {

            UserDto userDto = new UserDto();

            userDto.setSystemUserId(user.getSystemUserId());
            userDto.setEmail(user.getEmail());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setDepartmentId(user.getDepartment().getDepartmentId());
            userDto.setCreatedDate(user.getCreatedDate());

            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setDepartmentId(user.getDepartment().getDepartmentId());
            departmentDto.setName(user.getDepartment().getName());
            departmentDto.setColor(user.getDepartment().getColor());
            departmentDto.setUsers(null);

            userDto.setDepartment(departmentDto);

            userDtos.add(userDto);

        }

        return ResponseUtil.success(ResultMessage.createdSuccess, userDtos);
    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> get(UserDto userDtoFromRequest) {
        Optional<User> optionalUser = userRepository.findBySystemUserId(userDtoFromRequest.getSystemUserId());

        User user = optionalUser.orElse(null);

        if (user == null) {
            return ResponseUtil.successError(ResultMessage.userNotFound, null);
        }

        UserDto userDto = new UserDto();
        userDto.setSystemUserId(user.getSystemUserId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setDepartmentId(user.getDepartment().getDepartmentId());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setRole(user.getRole().toString());
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(user.getDepartment().getDepartmentId());
        departmentDto.setName(user.getDepartment().getName());
        departmentDto.setColor(user.getDepartment().getColor());
        userDto.setDepartment(departmentDto);

        return  ResponseUtil.success(ResultMessage.none, userDto);
    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> update(UserDto userDtoFromRequest) {
        Optional<User> optionalUser = userRepository.findBySystemUserId(userDtoFromRequest.getSystemUserId());

        User user = optionalUser.orElse(null);

        if (user == null) {
            return ResponseUtil.successError(ResultMessage.userNotFound, null);
        }

        Department department = departmentRepository.findByDepartmentId(user.getDepartment().getDepartmentId());
        if (department == null) {
            return ResponseUtil.successError(ResultMessage.departmentNotFound, null);
        }

        user.setName(userDtoFromRequest.getName());
        user.setLastName(userDtoFromRequest.getLastName());
        user.setEmail(userDtoFromRequest.getEmail());

        User updatedUser = userRepository.save(user);

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setSystemUserId(updatedUser.getSystemUserId());
        updatedUserDto.setEmail(updatedUser.getEmail());
        updatedUserDto.setName(updatedUser.getName());
        updatedUserDto.setLastName(updatedUser.getLastName());
        updatedUserDto.setDepartmentId(updatedUser.getDepartment().getDepartmentId());
        updatedUserDto.setCreatedDate(updatedUser.getCreatedDate());
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(updatedUser.getDepartment().getDepartmentId());
        departmentDto.setName(updatedUser.getDepartment().getName());
        departmentDto.setColor(updatedUser.getDepartment().getColor());
        updatedUserDto.setDepartment(departmentDto);

        return ResponseUtil.success(ResultMessage.updateSuccess, updatedUserDto);

    }

    @Override
    public ResponseEntity<ApiResponse<UserDto>> updateDepartment(UserDto userDtoFromRequest) {

        Optional<User> optionalUser = userRepository.findBySystemUserId(userDtoFromRequest.getSystemUserId());

        User user = optionalUser.orElse(null);
        if (user == null) {
            return ResponseUtil.successError(ResultMessage.userNotFound, null);
        }

        Department department = departmentRepository.findByDepartmentId(userDtoFromRequest.getDepartmentId());
        if (department == null) {
            return ResponseUtil.successError(ResultMessage.departmentNotFound, null);
        }

        DepartmentUser departmentUsers = departmentUserRepository.findByDepartmentAndUserAndActive(user.getDepartment(), user, true);

        if (departmentUsers == null) {

            user.setDepartment(department);
            userRepository.save(user);

            return addDepartmentUser(user, userDtoFromRequest);
        }

        user.setDepartment(department);
        userRepository.save(user);

        departmentUsers.setModifiedDate(new Date());
        departmentUsers.setActive(false);

        departmentUserRepository.save(departmentUsers);

        return  addDepartmentUser(user, userDtoFromRequest);
    }

    private ResponseEntity<ApiResponse<UserDto>> addDepartmentUser(User user, UserDto userDtoFromRequest) {
        Department newDepartment = departmentRepository.findByDepartmentId(userDtoFromRequest.getDepartmentId());

        DepartmentUser departmentUser = new DepartmentUser();
        departmentUser.setDepartment(newDepartment);
        departmentUser.setUser(user);
        departmentUser.setActive(true);
        departmentUser.setCreatedDate(new Date());

        DepartmentUser newDepartmentUser = departmentUserRepository.save(departmentUser);

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(newDepartment.getDepartmentId());
        departmentDto.setName(newDepartment.getName());

        UserDto userDto = new UserDto();
        userDto.setSystemUserId(user.getSystemUserId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setDepartment(departmentDto);

        DepartmentUserDto departmentUserDto = new DepartmentUserDto();
        departmentUserDto.setDepartmentId(newDepartment.getDepartmentId());
        departmentUserDto.setDepartment(departmentDto);
        departmentUserDto.setUser(userDto);
        departmentUserDto.setRole(user.getRole().toString());

        return  ResponseUtil.success(ResultMessage.updateSuccess, userDto);
    }

}
