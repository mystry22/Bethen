package com.bethen.bethen.dto;

public class WebData {
    private int id;
    private String fees;
    private String type;
    private String amount;
    private String status;
    private String reference;

    public WebData(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public WebData(int id, String fees, String type, String amount, String status, String reference) {
        this.id = id;
        this.fees = fees;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.reference = reference;
    }
}


