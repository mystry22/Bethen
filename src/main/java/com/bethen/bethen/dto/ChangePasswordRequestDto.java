package com.bethen.bethen.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {

    @NotNull(message = "Please provide a valid userId")
    private String userId;

    @NotNull(message = "Please provide a valid password")
    @Size(min = 8,max = 50, message = "Password must be between 8-50 chars")
    private  String uniqueKey;

    public String getUserId() {
        return userId;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
