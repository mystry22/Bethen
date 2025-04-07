package com.bethen.bethen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Document(collection = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsModel {

    private String userId;
    private  String type;
    private String amount;
    private  String status;
    @Id
    private String reference;


    private LocalDateTime planDate;

    private LocalDateTime dueDate;

    public LocalDateTime getPlanDate() {
        return planDate;
    }

    public void setPlanDate(LocalDateTime planDate, DateTimeFormatter formatter) {
        this.planDate = planDate;
    }

    public void setDueDate(LocalDateTime dueDate, DateTimeFormatter formatter) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private  LocalDateTime updatedAt;

    public String getUserId() {
        return userId;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setCreatedAt(LocalDateTime createdAt, DateTimeFormatter formatter) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt, DateTimeFormatter formatter) {
        this.updatedAt = updatedAt;
    }
}
