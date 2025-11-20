package com.smartcinema.cinema_api.dto;
import com.fasterxml.jackson.annotation.JsonInclude;

public class LoginResponse {
    private int statusCode;
    private String message;
    private boolean success;
    private long timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    public LoginResponse(){

    }

    public LoginResponse(int statusCode, String message, boolean success, long timestamp, String token, String firstName) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = success;
        this.timestamp = timestamp;
        this.token = token;
        this.firstName = firstName;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                ", token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
