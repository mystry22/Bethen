package com.bethen.bethen.dto;

public class WebCustomer {
    private int id;
    private String email;
    private String phone;
    private  String domain;
    private String status;
    private String metadata;
    private String last_name;
    private String first_name;
    private String customer_code;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDomain() {
        return domain;
    }

    public String getStatus() {
        return status;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }
}
