package com.bethen.bethen.dto;

import com.bethen.bethen.validation.ValidateAmount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GenerateAccountDto {

    @Email(message = "Kindly provide a valid email address")
    private String email;

    @ValidateAmount(message = "Please provide a valid amount")
    private String amount;

    @NotNull(message = "Currency is required")
    private String currency;

    @NotNull(message = "Reference must not be null")
    private String reference;

    @NotNull(message = "Please provide a name for the account")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
