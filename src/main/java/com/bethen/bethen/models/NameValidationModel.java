package com.bethen.bethen.models;

public class NameValidationModel {

    private String bank_code;
    private String account_number;
    private String currency;

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBank_code() {
        return bank_code;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getCurrency() {
        return currency;
    }
}
