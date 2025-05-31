package com.bethen.bethen.services;

import com.bethen.bethen.dto.ActivatePlanRequestDto;
import com.bethen.bethen.dto.PaymentLinkRequestDto;
import com.bethen.bethen.interfaces.TransactionsInter;
import com.bethen.bethen.models.*;
import com.bethen.bethen.repos.InvestmentRepo;
import com.bethen.bethen.repos.MembersRepo;
import com.bethen.bethen.repos.TransactionRepo;
import com.bethen.bethen.util.Helper;
import com.bethen.bethen.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.*;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransactionsService implements TransactionsInter {
    @Autowired
    private  HttpServices httpServices;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private MembersRepo membersRepo;

    @Autowired
    private InvestmentRepo investmentRepo;

    @Autowired
    ModelMapper modelMapper;

    //Generate payment link
    @Override
    public Object generatePaymentLink(PaymentLinkRequestDto paymentLinkRequestDto, String token) {
        System.out.println("I see am oo");
        String reference = Helper.generateReference();
        paymentLinkRequestDto.setReference(reference);
        Object response = httpServices.getPaymentLink("transaction/initialize",paymentLinkRequestDto).block();
        if(response.equals("Client Error")){
            return "Client Error";
        } else if (response.equals("Server Error")) {
            return  "Server Error";
        }else {
            String  userId = (String) jwtUtil.extractId(token);

            TransactionsModel transactionsModel = new TransactionsModel();
            transactionsModel.setAmount(paymentLinkRequestDto.getAmount());
            transactionsModel.setReference(reference);
            transactionsModel.setStatus("pending");
            transactionsModel.setUserId(userId);
            transactionsModel.setType("funding");
            transactionsModel.setCreatedAt(Helper.generateTodayDateAndTime(), Helper.dateTimeFormatter());

            //save transaction to db
            transactionRepo.insert(transactionsModel);

            //send response to user
            return response;
        }
        // return paymentResponse;
    }

    //Update payment
    @Override
    public String updatePayment(String reference, String amount) {

        //Get reference

        TransactionsModel transactionsModel = transactionRepo.findById(reference).orElse(null);

        if (transactionsModel != null){
            //update to paid if successful
            transactionsModel.setStatus("success");
            transactionsModel.setUpdatedAt(Helper.generateTodayDateAndTime(), Helper.dateTimeFormatter());
            transactionRepo.save(transactionsModel);

            //Update balance
           double amt = Double.parseDouble(amount);
          MemberModel memberModel = membersRepo.findById(transactionsModel.getUserId()).orElse(null);
            double newModel = memberModel.getBalance() + amt;
            memberModel.setBalance(newModel);
            membersRepo.save(memberModel);



            return  "updated";
        }

        //return string
        return "transaction does not exist";
    }

    //Get all transactions
    @Override
    public List<TransactionsModel> allTransactions() {
        return transactionRepo.findAll();
    }

    @Override
    public String activatePlan(ActivatePlanRequestDto activatePlanRequestDto, String token) {

        //Get user id
        String userId = jwtUtil.extractId(token).toString();

        //get user details
        MemberModel memberModel = membersRepo.findById(userId).orElse(null);

        //get amount from request
        double amount = Double.parseDouble(activatePlanRequestDto.getAmount());

        if(memberModel != null) {
            //compare investment amount and balance
            if(memberModel.getBalance() >= amount){
                //get today's date
                LocalDateTime today = Helper.generateTodayDateAndTime();
                //set maturity date plus 90days
                LocalDateTime maturity = today.plusDays(90);

                //create investment model objects
                InvestmentModel investmentModel = new InvestmentModel();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                investmentModel.setEndDate(maturity,Helper.dateTimeFormatter());
                investmentModel.setStartDate(today, Helper.dateTimeFormatter());
                investmentModel.setAmount(amount);
                investmentModel.setUserId(userId);

                //Save to investment db
                investmentRepo.insert(investmentModel);

                //Update user balance
                double newBalance = memberModel.getBalance() - amount;
                memberModel.setBalance(newBalance);
                membersRepo.save(memberModel);

                //Credit super admin account

                //Save transaction to transactions table
                TransactionsModel transactionsModel = new TransactionsModel();
                transactionsModel.setUpdatedAt(Helper.generateTodayDateAndTime(), Helper.dateTimeFormatter());
                transactionsModel.setUserId(userId);
                transactionsModel.setType("investment");
                transactionsModel.setStatus("active");
                transactionsModel.setCreatedAt(Helper.generateTodayDateAndTime(),Helper.dateTimeFormatter());
                transactionsModel.setReference(Helper.generateInvestmentReference());

                transactionRepo.insert(transactionsModel);

                return "investment initiated successfully";
            }else {
                return "insufficient balance";
            }

        }

        return "user does not exist";
    }

    @Override
    public List<TransactionsResponseModel> getUserTransactions(String token) {

        //return claims
        Claims claims = (Claims) jwtUtil.getTotalClaims(token);
        JWTResponseModel responseModel = new JWTResponseModel();
        String userId = responseModel.setUserId((String) claims.get("userId"));
        //find using userId
        List<TransactionsResponseModel> response = transactionRepo.getTransactionsByUserId(userId)
                .orElse(Collections.emptyList()) // handle missing data safely
                .stream()
                .map(transaction -> modelMapper.map(transaction, TransactionsResponseModel.class))
                .collect(Collectors.toList());

        return response;

    }

    @Override
    public List<BankList> getBankList() {
       return (List<BankList>) httpServices.getBankList();
    }

    @Override
    public NameValidationResponseModel validateName(NameValidationModel nameValidationModel) {
        return httpServices.nameEnquiry(nameValidationModel);
    }

    public  String calculateHmacSha512(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        // HmacSHA512 is the algorithm name for SHA-512 HMAC
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(secretKeySpec);
        byte[] hash = hmacSha512.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }




}
