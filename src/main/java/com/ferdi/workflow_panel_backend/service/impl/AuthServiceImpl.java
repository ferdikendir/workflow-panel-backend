package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import com.ferdi.workflow_panel_backend.dto.AuthDto;
import com.ferdi.workflow_panel_backend.dto.DepartmentDto;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.Department;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.payload.ResponseUtil;
import com.ferdi.workflow_panel_backend.repository.DepartmentRepository;
import com.ferdi.workflow_panel_backend.repository.UserRepository;
import com.ferdi.workflow_panel_backend.security.JwtUtil;
import com.ferdi.workflow_panel_backend.service.AuthService;
import com.ferdi.workflow_panel_backend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ApiResponse<UserDto>> register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseUtil.successError(ResultMessage.sameEmailRegistered, null);
        }

        User user = new User();
        user.setName(userDto.getName().toUpperCase(Locale.ROOT));
        user.setLastName(userDto.getLastName().toUpperCase(Locale.ROOT));
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));



        Department department = departmentRepository.findByDepartmentId(userDto.getDepartmentId());

        if (department == null) {
            return ResponseUtil.successError(ResultMessage.departmentNotFound, null);
        }

        user.setDepartment(department);
        user.setRole(User.Role.EMPLOYEE);

        User savedUser = userRepository.save(user);

        UserDto savedUserDto = new UserDto();

        savedUserDto.setSystemUserId(savedUser.getSystemUserId());
        savedUserDto.setName(userDto.getName());
        savedUserDto.setLastName(userDto.getLastName());
        savedUserDto.setEmail(savedUser.getEmail());

        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setDepartmentId(department.getDepartmentId());
        departmentDto.setName(department.getName());

        savedUserDto.setDepartmentId(departmentDto.getDepartmentId());
        savedUserDto.setDepartment(departmentDto);

        return ResponseUtil.success(ResultMessage.createdSuccess, savedUserDto);
    }

    @Override
    public ResponseEntity<ApiResponse<AuthDto>> login(String email, String password) {
        return getUser(email, password, userRepository, jwtUtil);
    }

    private ResponseEntity<ApiResponse<AuthDto>> getUser(String email, String password, UserRepository userRepository, JwtUtil jwtUtil) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {

            User loggedUser = user.get();

            AuthDto authDto = convertUserToAuthDto(loggedUser);

            authDto.setToken(jwtUtil.generateToken(loggedUser.getSystemUserId()));
            authDto.setRefreshToken(refreshTokenService.createRefreshToken(loggedUser.getSystemUserId()).getToken());

            return ResponseUtil.success(ResultMessage.loginSuccess,authDto);
        }

        return ResponseUtil.successError(ResultMessage.userNotFound, null);
    }

    @Override
    public ResponseEntity<ApiResponse<User>> updatePassword(UserDto userDto) {
        return null;
    }

    private AuthDto convertUserToAuthDto(User user) {
        AuthDto authDto = new AuthDto();

        UserDto userDto = new UserDto();

        userDto.setSystemUserId(user.getSystemUserId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentId(user.getDepartment().getDepartmentId());
        departmentDto.setName(user.getDepartment().getName());

        userDto.setDepartment(departmentDto);
        userDto.setDepartmentId(departmentDto.getDepartmentId());

        authDto.setUser(userDto);
        return authDto;
    }

}
