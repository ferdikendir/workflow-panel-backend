package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.payload.ApiResponse;
import com.ferdi.workflow_panel_backend.payload.ResponseUtil;
import com.ferdi.workflow_panel_backend.repository.UserRepository;
import com.ferdi.workflow_panel_backend.security.JwtUtil;
import com.ferdi.workflow_panel_backend.service.AuthService;
import com.ferdi.workflow_panel_backend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ApiResponse<User>> register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            return ResponseUtil.successError(ResultMessage.sameEmailRegistered, null);
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(User.Role.EMPLOYEE);

        return ResponseUtil.success(ResultMessage.createdSuccess, userRepository.save(user));
    }

    @Override
    public ResponseEntity<ApiResponse<User>> login(String email, String password) {
        return getUser(email, password, userRepository, jwtUtil);
    }

    private ResponseEntity<ApiResponse<User>> getUser(String email, String password, UserRepository userRepository, JwtUtil jwtUtil) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            User loggedUser = user.get();
            loggedUser.token = jwtUtil.generateToken(loggedUser.getSystemUserId());
            loggedUser.setRefreshToken(refreshTokenService.createRefreshToken(loggedUser.getSystemUserId()).getToken());
            return ResponseUtil.success(ResultMessage.loginSuccess,loggedUser);
        }

        return ResponseUtil.successError(ResultMessage.userNotFound, null);
    }

    @Override
    public ResponseEntity<ApiResponse<User>> updatePassword(UserDto userDto) {
        return null;
    }

}
