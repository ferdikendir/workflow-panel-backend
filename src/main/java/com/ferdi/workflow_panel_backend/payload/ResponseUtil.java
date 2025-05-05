package com.ferdi.workflow_panel_backend.payload;

import com.ferdi.workflow_panel_backend.constant.ResultMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success(ResultMessage.ResultMessageBase resultMessage, T data) {
        return ResponseEntity.ok(new ApiResponse<T>(true, resultMessage, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> successError(ResultMessage.ResultMessageBase resultMessage, T data) {
        return ResponseEntity.ok(new ApiResponse<T>(false, resultMessage, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponse<>(false, new ResultMessage.ResultMessageBase(message, 1), null), status);
    }
}
