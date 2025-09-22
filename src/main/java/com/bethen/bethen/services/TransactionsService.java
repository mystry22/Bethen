package com.bethen.bethen.services;

import com.bethen.bethen.dto.*;
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
import java.util.Objects;
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
    private MailingService mailingService;

    @Autowired
    private MembersRepo membersRepo;

    @Autowired
    private InvestmentRepo investmentRepo;

    @Autowired
    ModelMapper modelMapper;

    //Generate payment link
    @Override
    public Object generatePaymentLink(PaymentLinkRequestDto paymentLinkRequestDto, String token) {
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
    public String updatePayment(WebhookResponseDto webhookResponseDto) {

        //Get reference

        TransactionsModel transactionsModel = transactionRepo.findById(webhookResponseDto.getData().getReference()).orElse(null);

        if (transactionsModel != null){
            //update to paid if successful
            transactionsModel.setStatus(webhookResponseDto.getData().getStatus());
            transactionsModel.setUpdatedAt(Helper.generateTodayDateAndTime(), Helper.dateTimeFormatter());
            transactionRepo.save(transactionsModel);

            //check if success
            if(webhookResponseDto.getData().getStatus().equals("success")){
                //Update balance
                double amt = Double.parseDouble(webhookResponseDto.getData().getAmount());
                MemberModel memberModel = membersRepo.findById(transactionsModel.getUserId()).orElse(null);
                double newModel = memberModel.getBalance() + amt;
                memberModel.setBalance(newModel);
                membersRepo.save(memberModel);
                return  "updated";
            }


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
        String amoutToString = String.valueOf(amount);
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
                investmentModel.setAmount(amoutToString);
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
                transactionsModel.setAmount(amoutToString);
                transactionsModel.setCreatedAt(Helper.generateTodayDateAndTime(),Helper.dateTimeFormatter());
                transactionsModel.setReference(Helper.generateInvestmentReference());

                transactionRepo.insert(transactionsModel);

                //mail params
                PlanNotificationModel planNotificationModel = new PlanNotificationModel(memberModel.getEmail(),activatePlanRequestDto.getAmount(),memberModel.getFirstName());

                //send mail
                String mailResponse = mailingService.notificationOfPlan(planNotificationModel);
                if(mailResponse.equals("mail sent")){
                    return "investment initiated successfully";
                }else {
                    return  "nawao";
                }


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

        return transactionRepo.getTransactionsByUserId(userId)
                .orElse(Collections.emptyList()) // handle missing transactions
                .stream()
                .map(transaction -> modelMapper.map(transaction, TransactionsResponseModel.class))
                .collect(Collectors.toList());


    }

    @Override
    public List<BankList> getBankList() {
       return (List<BankList>) httpServices.getBankList();
    }

    @Override
    public NameValidationResponseModel validateName(NameValidationModel nameValidationModel) {
        return httpServices.nameEnquiry(nameValidationModel);
    }

    @Override
    public CustomReturnResponse doPayout(String token, PayoutDto payoutDto) {
        //get user details from token
        Claims claims = (Claims) jwtUtil.getTotalClaims(token);
        //get user email
        String email = (String) claims.get("email");
        //deduct amount
        MemberModel memberModel = membersRepo.findByEmail(email).orElse(null);

        if(memberModel != null){
            //save new changes
            Double newBal = memberModel.getBalance() - (Double.parseDouble(payoutDto.getAmount()));
            if(newBal < 0){
                CustomReturnResponse customReturnResponse = new CustomReturnResponse(false,"Insufficient funds");
                return customReturnResponse;
            }

            memberModel.setBalance(newBal);
            membersRepo.save(memberModel);

            //register in transaction model
            TransactionsModel transactionsModel = new TransactionsModel();
            transactionsModel.setCreatedAt(Helper.generateTodayDateAndTime(),Helper.dateTimeFormatter());
            transactionsModel.setType("Payout");
            transactionsModel.setStatus("success");
            transactionsModel.setUserId(memberModel.getUserId());
            transactionsModel.setReference(Helper.generateReference());
            transactionsModel.setAmount(payoutDto.getAmount());

            transactionRepo.insert(transactionsModel);

            //notify admin via mail
            mailingService.notificationOfPayout(memberModel.getEmail(),payoutDto.getAmount(),payoutDto.getAccountNumber(),payoutDto.getBank());
            CustomReturnResponse customReturnResponse = new CustomReturnResponse(true,"Payout initiated successfully");
            return customReturnResponse;
        }
        return null;
    }

    @Override
    public InvestmentModel getInvestmentData(String token) {
        //get token from claims
        Claims claims = (Claims) jwtUtil.getTotalClaims(token);
        String userId = claims.get("userId").toString();
        InvestmentModel investmentModel = investmentRepo.findById(userId).orElse(null);

        if(investmentModel != null){
            return investmentModel;
        }

        //get invest data and return same
        return null;
    }

    @Override
    public GenerateAccountResponse generateAccount(GenerateAccountDto generateAccountDto, String token) {
        //Extract token claims to get userId
        Claims claims = (Claims) jwtUtil.getTotalClaims(token);
        String userId = claims.get("userId").toString();

        //Register transaction
        TransactionsModel regTrans = new TransactionsModel();
        regTrans.setAmount(generateAccountDto.getAmount());
        regTrans.setUserId(userId);
        regTrans.setType("funding");
        regTrans.setReference(generateAccountDto.getReference());
        regTrans.setStatus("pending");
        regTrans.setCreatedAt(Helper.generateTodayDateAndTime(),Helper.dateTimeFormatter());
        transactionRepo.save(regTrans);
        //Generate account details
        return  httpServices.getAccountDetails(generateAccountDto);
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
