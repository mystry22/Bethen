package com.bethen.bethen.dto;

import lombok.Data;

@Data
public class WebhookResponseDto {

    private  String notify;
    private String notifyType;
    private WebData data;

    public String getNotify() {
        return notify;
    }

    public String getNotifyType() {
        return notifyType;
    }

    public WebData getData() {
        return data;
    }


}
