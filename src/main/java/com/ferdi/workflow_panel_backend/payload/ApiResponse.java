package com.ferdi.workflow_panel_backend.payload;

import lombok.Data;

@Data
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private T data;

    // Constructors
    public ApiResponse() {}

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
