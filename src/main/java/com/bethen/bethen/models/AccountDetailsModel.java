package com.bethen.bethen.models;

public class AccountDetailsModel {

    private String account_name;
    private String account_number;
    private  String bank_name;

    public AccountDetailsModel(){}

    public AccountDetailsModel(String account_name, String account_number, String bank_name) {
        this.account_name = account_name;
        this.account_number = account_number;
        this.bank_name = bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
