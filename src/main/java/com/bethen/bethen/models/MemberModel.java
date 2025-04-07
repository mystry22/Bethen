package com.bethen.bethen.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberModel {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String phone;
    @Indexed(unique = true)
    private String email;
    private double balance;
    private String status;
    private String uniqueKey;
    //private Role role;
    private String role;
    private LocalDateTime regDate;

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate, DateTimeFormatter formatter) {
        this.regDate = regDate;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public Role getRole() {
//        return role;
//    }

    public String getPrivateKey() {
        return uniqueKey;
    }

    public void setPrivateKey(String privateKey) {
        this.uniqueKey = privateKey;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MemberModel(String userId) {
        this.userId = String.valueOf(UUID.fromString(UUID.randomUUID().toString()));
    }
}
