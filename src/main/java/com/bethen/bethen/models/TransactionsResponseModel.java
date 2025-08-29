package com.bethen.bethen.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransactionsResponseModel {

    private String type;
    private String amount;
    private String status;
    private LocalDateTime createdAt;
    private String _id;

    public String getType() {
        return type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    public TransactionsResponseModel(String type, String amount, String status, LocalDateTime createdAt, String _id) {
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
        this._id = _id;
    }
}
