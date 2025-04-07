package com.bethen.bethen.msg;

import lombok.Data;

@Data
public class Msg {

    private boolean status;
    private  String message;

    public Msg(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
