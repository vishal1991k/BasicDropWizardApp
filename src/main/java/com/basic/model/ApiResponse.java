package com.basic.model;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public ApiResponse(T data) {
        this.success = true;
        this.data = data;
        this.message = "Operation completed successfully.";
    }

    public ApiResponse(String message) {
        this.success = false;
        this.message = message;
        this.data = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
