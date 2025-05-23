package com.ferdi.workflow_panel_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferdi.workflow_panel_backend.entity.User;
import com.ferdi.workflow_panel_backend.constant.PublicApiList;
import com.ferdi.workflow_panel_backend.repository.UserRepository;
import com.ferdi.workflow_panel_backend.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String path = request.getServletPath();
        if (PublicApiList.getPublicApiList().contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt;
        final UUID systemUserId;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Token bulunamadı veya geçersiz formatta.");
            return;
        }

        jwt = authHeader.substring(7);
        systemUserId = jwtUtil.extractSystemUserId(jwt);

        if (systemUserId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> optionalUser = userRepository.findBySystemUserId(systemUserId);

            if (optionalUser.isEmpty()) {
                sendUnauthorizedResponse(response, "Kullanıcı bulunamadı.");
                return;
            }

            User user = optionalUser.get();
            Collection permissions = new ArrayList<>(User.Role.ADMIN.ordinal());


            if (!jwtUtil.isTokenValid(jwt, user)) {
                sendUnauthorizedResponse(response, "Token geçerli değil veya süresi dolmuş.");
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.emptyList()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new ObjectMapper().writeValueAsString(Map.of(
                "success", false,
                "message", message
        ));

        response.getWriter().write(json);
    }
}

