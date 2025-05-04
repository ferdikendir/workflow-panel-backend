package com.ferdi.workflow_panel_backend.service;

import com.ferdi.workflow_panel_backend.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(UUID systemUserId);
    boolean isRefreshTokenValid(RefreshToken refreshToken);
}

