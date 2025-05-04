package com.ferdi.workflow_panel_backend.constant;

import java.util.List;

public class PublicApiList {

    private static final List<String> PUBLIC_API_LIST = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh"
    );

    public static List<String> getPublicApiList() {
        return PUBLIC_API_LIST;
    }
}
