package com.bethen.bethen.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BankList {

    private String bank_name;
    private String bank_code;
    private String logo;

    public String getBank_name() {
        return bank_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public String getLogo() {
        return logo;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BankList(String bank_name, String bank_code, String logo) {
        this.bank_name = bank_name;
        this.bank_code = bank_code;
        this.logo = logo;
    }
}
