package com.ferdi.workflow_panel_backend.payload;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import lombok.Data;

@Data
public class ApiResponse <T>{
    private boolean success;
    private ResultMessage.ResultMessageBase resultMessage;
    private T data;

    // Constructors
    public ApiResponse() {}

    public ApiResponse(boolean success, ResultMessage.ResultMessageBase resultMessage, T data) {
        this.success = success;
        this.resultMessage = resultMessage;
        this.data = data;
    }
}
