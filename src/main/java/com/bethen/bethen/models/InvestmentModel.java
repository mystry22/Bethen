package com.bethen.bethen.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class InvestmentModel {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double amount;
    private  String status;
    @Id
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setStartDate(LocalDateTime startDate, DateTimeFormatter formatter) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate, DateTimeFormatter formatter) {
        this.endDate = endDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
