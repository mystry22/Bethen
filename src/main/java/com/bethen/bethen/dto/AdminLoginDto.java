package com.bethen.bethen.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class AdminLoginDto {

    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Please provide a valid password")
    @Size(min = 8,max = 50, message = "Password must be between 8-50 chars")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
