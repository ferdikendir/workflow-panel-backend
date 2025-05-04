package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.dto.UserDto;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.repository.UserRepository;
import com.ferdi.workflow_panel_backend.security.JwtUtil;
import com.ferdi.workflow_panel_backend.service.AuthService;
import com.ferdi.workflow_panel_backend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User register(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(User.Role.EMPLOYEE);

        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        return getUser(email, password, userRepository, jwtUtil);
    }

    private User getUser(String email, String password, UserRepository userRepository, JwtUtil jwtUtil) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            User loggedUser = user.get();
            loggedUser.token = jwtUtil.generateToken(loggedUser.getSystemUserId());
            loggedUser.setRefreshToken(refreshTokenService.createRefreshToken(loggedUser.getSystemUserId()).getToken());
            return loggedUser;
        }

        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public User updatePassword(UserDto userDto) {
        return null;
    }

}
