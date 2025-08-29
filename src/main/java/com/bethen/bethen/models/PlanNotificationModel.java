package com.bethen.bethen.models;

public class PlanNotificationModel {

    private String email;
    private String amount;
    private String firstName;

    public PlanNotificationModel(String email, String amount, String firstName) {
        this.email = email;
        this.amount = amount;
        this.firstName = firstName;
    }

    public PlanNotificationModel(){}

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
