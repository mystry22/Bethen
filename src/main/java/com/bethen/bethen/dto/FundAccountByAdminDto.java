package com.bethen.bethen.dto;

public class FundAccountByAdminDto {

    private String amount;
    private String email;

    public FundAccountByAdminDto(String amount, String email) {
        this.amount = amount;
        this.email = email;
    }

    public FundAccountByAdminDto(){}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
