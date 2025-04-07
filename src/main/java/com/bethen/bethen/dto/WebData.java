package com.bethen.bethen.dto;

public class WebData {
    private int id;
    private String currency;
    private String amount;
    private String reference;
    private String ip_address;
    private  String channel;
    private  String type;
    private  String domain;
    private String plan;
    private String message;
    private  String paid_at;
    private String bankcode;
    private  String bankname;
    private WebCustomer customer;
    private String metadata;
    private String craccount;
    private String narration;
    private  String sessionid;
    private  String created_at;
    private String updated_at;
    private String card_attempt;
    private String craccountname;
    private String originatorname;
    private String requested_amount;
    private  String settlement_batchid;
    private String originatoraccountnumber;

    public void setCustomer(WebCustomer customer) {
        this.customer = customer;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setCraccount(String craccount) {
        this.craccount = craccount;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setCard_attempt(String card_attempt) {
        this.card_attempt = card_attempt;
    }

    public void setCraccountname(String craccountname) {
        this.craccountname = craccountname;
    }

    public void setOriginatorname(String originatorname) {
        this.originatorname = originatorname;
    }

    public void setRequested_amount(String requested_amount) {
        this.requested_amount = requested_amount;
    }

    public void setSettlement_batchid(String settlement_batchid) {
        this.settlement_batchid = settlement_batchid;
    }

    public void setOriginatoraccountnumber(String originatoraccountnumber) {
        this.originatoraccountnumber = originatoraccountnumber;
    }

    public WebCustomer getCustomer() {
        return customer;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getCraccount() {
        return craccount;
    }

    public String getNarration() {
        return narration;
    }

    public String getSessionid() {
        return sessionid;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCard_attempt() {
        return card_attempt;
    }

    public String getCraccountname() {
        return craccountname;
    }

    public String getOriginatorname() {
        return originatorname;
    }

    public String getRequested_amount() {
        return requested_amount;
    }

    public String getSettlement_batchid() {
        return settlement_batchid;
    }

    public String getOriginatoraccountnumber() {
        return originatoraccountnumber;
    }

    public WebData(int id, String currency, String amount, String reference, String ip_address, String channel, String type, String domain, String plan, String message, String paid_at, String bankcode, String bankname) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.reference = reference;
        this.ip_address = ip_address;
        this.channel = channel;
        this.type = type;
        this.domain = domain;
        this.plan = plan;
        this.message = message;
        this.paid_at = paid_at;
        this.bankcode = bankcode;
        this.bankname = bankname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPaid_at(String paid_at) {
        this.paid_at = paid_at;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public int getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getChannel() {
        return channel;
    }

    public String getType() {
        return type;
    }

    public String getDomain() {
        return domain;
    }

    public String getPlan() {
        return plan;
    }

    public String getMessage() {
        return message;
    }

    public String getPaid_at() {
        return paid_at;
    }

    public String getBankcode() {
        return bankcode;
    }

    public String getBankname() {
        return bankname;
    }
}
