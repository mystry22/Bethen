package com.bethen.bethen.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Please provide a valid password")
    @Size(min = 8,max = 50, message = "Password must be between 8-50 chars")
    private String uniqueKey;

    public String getEmail() {
        return email;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
