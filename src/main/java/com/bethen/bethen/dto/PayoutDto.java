package com.bethen.bethen.dto;

public class PayoutDto {

    private String accountNumber;
    private String bank;
    private String amount;

    public PayoutDto(){}

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public PayoutDto(String accountNumber, String bank, String amount) {
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.amount = amount;
    }
}
