package com.ferdi.workflow_panel_backend.service.impl;

import com.ferdi.workflow_panel_backend.entity.RefreshToken;
import com.ferdi.workflow_panel_backend.repository.RefreshTokenRepository;
import com.ferdi.workflow_panel_backend.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repo;

    @Override
    public RefreshToken createRefreshToken(UUID systemUserId) {
        RefreshToken token = new RefreshToken();
        token.setSystemUserId(systemUserId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        return repo.save(token);
    }

    @Override
    public boolean isRefreshTokenValid(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isAfter(Instant.now());
    }
}
