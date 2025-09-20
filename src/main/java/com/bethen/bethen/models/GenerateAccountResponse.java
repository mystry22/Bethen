package com.bethen.bethen.models;

public class GenerateAccountResponse {

    private boolean status;
    private String message;
    private AccountDetailsModel data;

    public GenerateAccountResponse(){}

    public GenerateAccountResponse(boolean status, String message, AccountDetailsModel data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public AccountDetailsModel getData() {
        return data;
    }

    public void setData(AccountDetailsModel data) {
        this.data = data;
    }
}
