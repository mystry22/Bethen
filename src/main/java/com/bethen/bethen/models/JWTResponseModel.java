package com.bethen.bethen.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class JWTResponseModel {

    private String firstName;
    private String lastName;
    private String role;
    private String userId;
    private String email;
    private  String sub;
    private LocalDateTime exp;
    private  LocalDateTime iat;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String setUserId(String userId) {
        this.userId = userId;
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public void setIat(LocalDateTime iat) {
        this.iat = iat;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getSub() {
        return sub;
    }

    public LocalDateTime getExp() {
        return exp;
    }

    public LocalDateTime getIat() {
        return iat;
    }
}
