package com.bethen.bethen.dto;

import com.bethen.bethen.validation.ValidateAmount;
import com.bethen.bethen.validation.ValidateCurrency;
import com.bethen.bethen.validation.ValidateFullName;
import com.bethen.bethen.validation.ValidateTextChars;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentLinkRequestDto {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Please provide a valid email address")
    private String email;

    private String reference;

    @ValidateCurrency(message = "Please provide a valid currency")
    private String currency;

    @ValidateAmount(message = "Please provide a valid amount")
    private String amount;

    @NotNull(message = "Please provide a valid callback url")
    private String callback;

    @ValidateFullName(message = "Please provide a valid full name")
    private String full_name;

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public PaymentLinkRequestDto(String email, String currency, String amount, String callback, String full_name, String reference) {
        this.email = email;
        this.currency = currency;
        this.amount = amount;
        this.callback = callback;
        this.full_name = full_name;
        this.reference = reference;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public String getCallback() {
        return callback;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}


