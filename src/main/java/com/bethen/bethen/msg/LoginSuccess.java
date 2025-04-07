package com.bethen.bethen.msg;

import lombok.Data;
@Data
public class LoginSuccess {
    private boolean status;
    private String token;

    public LoginSuccess(boolean status, String token) {
        this.status = status;
        this.token = token;
    }
}



