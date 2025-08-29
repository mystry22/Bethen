package com.bethen.bethen.models;

public class CustomReturnResponse {

    private boolean status;
    private String message;

    public CustomReturnResponse(){}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CustomReturnResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
