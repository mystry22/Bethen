package com.bethen.bethen.dto;

public class CustomResponse<T> {
    private String message;
    private boolean status;
    private  T data;

    public CustomResponse(String message, boolean status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
