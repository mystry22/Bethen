package com.bethen.bethen.dto;

import com.bethen.bethen.validation.ValidateAmount;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActivatePlanRequestDto {

    @ValidateAmount(message = "Please provide a valid amount")
    private String amount;



    public String getAmount() {
        return amount;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }
}
