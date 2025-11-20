package com.smartcinema.cinema_api.dto;

public class LoginResponse {
    private int statusCode;
    private String message;
    private boolean success;
    private long timestamp;

    public LoginResponse(){

    }

    public LoginResponse(int statusCode, String message, boolean success, long timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                '}';
    }
}
