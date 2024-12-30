package com.tripsync.trip_management.dto.response;

import org.springframework.http.HttpStatus;

import java.util.List;


public class ApiErrorResponse {
    private HttpStatus statusName;
    private int status;
    private String message;
    private List<String> errors;

    public HttpStatus getStatusName() {
        return statusName;
    }

    public void setStatusName(HttpStatus statusName) {
        this.statusName = statusName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
