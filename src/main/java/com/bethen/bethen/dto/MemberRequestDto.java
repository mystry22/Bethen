package com.bethen.bethen.dto;

import com.bethen.bethen.validation.ValidatePhoneNumber;
import com.bethen.bethen.validation.ValidateTextChars;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import  com.bethen.bethen.validation.ValidateNameImplementation;


@Data
public class MemberRequestDto {
    @Valid


    @ValidateTextChars(message = "Please provide a valid first name")
    private String firstName;

    @ValidateTextChars(message = "Please provide a valid first name")
    private String lastName;

    @ValidatePhoneNumber(message = "Please provide a valid phone number")
    private String phone;

    @Email(message = "Please provide a valid email address ")
    @NotNull(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Balance cannot be empty")
    @Min(value = 0, message = "Balance must not contain a negative value")
    private double balance;

    @NotNull(message = "Please provide a valid status")
    private String status;

    @NotNull(message = "Please provide a valid password")
    @Size(min = 8,max = 50, message = "Password must be between 8-50 chars")
    private  String uniqueKey;

//    @NotNull(message = "Please provide a valid role")
//    private String role;

//    public String getRole() {
//        return role;
//    }

//    public void setRole(String role) {
//        this.role = role;
//    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }


}
