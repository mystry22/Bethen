package com.bethen.bethen.controllers;

import com.bethen.bethen.dto.*;
import com.bethen.bethen.dto.post.PaymentResponse;
import com.bethen.bethen.models.*;
import com.bethen.bethen.msg.Msg;
import com.bethen.bethen.services.HttpServices;
import com.bethen.bethen.services.TransactionsService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @Value("${jwt_secret}") String WORKING_SECRET;


    //Generate payment link
    @PostMapping("/paymentLink")
    public ResponseEntity<?> generatePaymentLink(@Valid  @RequestBody PaymentLinkRequestDto paymentLinkRequestDto,
                                                 BindingResult bindingResult,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, List<String>> errorMap = new HashMap<>();

            for (FieldError error : errors) {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errorMap.computeIfAbsent(field, k -> new java.util.ArrayList<>()).add(message);
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        } else {
            String token = authorizationHeader.substring(7);
            return new ResponseEntity<>(transactionsService.generatePaymentLink(paymentLinkRequestDto, token), HttpStatus.CREATED);

        }
    }



    //Webhook

    @PostMapping("/webhook")
    public ResponseEntity<?> receiveWebhook(@RequestBody WebhookResponseDto webhook){
        String response = transactionsService.updatePayment(webhook.getData().getReference(), webhook.getData().getAmount());

        if ((response.equals("updated"))){
            Msg msg = new Msg(true, "Transaction updated successfully");
            return  new ResponseEntity<>(msg, HttpStatus.OK);
        }else {
            Msg msg = new Msg(false, "Transaction reference does not exist");
            return  new ResponseEntity<>(msg, HttpStatus.OK);

        }

    }

    //Activate plan
    @PostMapping("/activate")
    public ResponseEntity<?> activatePlan(@Valid @RequestBody ActivatePlanRequestDto activatePlanRequestDto,
                                          BindingResult bindingResult, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader ){
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, List<String>> errorMap = new HashMap<>();

            for (FieldError error : errors) {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errorMap.computeIfAbsent(field, k -> new java.util.ArrayList<>()).add(message);
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        } else {
            String token = authorizationHeader.substring(7);

            String isActivated = transactionsService.activatePlan(activatePlanRequestDto, token);

            if(isActivated.equals("investment initiated successfully")){
                Msg msg = new Msg(true, "Investment initiated successfully");
                return new ResponseEntity<Msg>(msg, HttpStatus.OK);
            } else if (isActivated.equals("insufficient balance")) {
                Msg msg = new Msg(false, "Insufficient balance");
                return new ResponseEntity<Msg>(msg, HttpStatus.OK);
            }else {
                Msg msg = new Msg(false, "Ensure user exists");
            }

            return  new ResponseEntity<>("Okay", HttpStatus.OK);
        }


    }

    //Disable plan

    //Transaction history
    @GetMapping("/allTransactions")
    public ResponseEntity<List<TransactionsModel>> getAllTransactions(){
        return new ResponseEntity<>(transactionsService.allTransactions(), HttpStatus.OK);
    }

    //Transaction history for a user
    @GetMapping("/userTransaction")
    public ResponseEntity<List<TransactionsResponseModel>> getAllUserTransaction(@RequestHeader (HttpHeaders.AUTHORIZATION) String authorization){
        String token = authorization.substring(7);
        return new ResponseEntity<>(transactionsService.getUserTransactions(token), HttpStatus.OK);
    }

    @GetMapping("/bankList")
    public ResponseEntity<?> getBanklist(){
        return new ResponseEntity<>(transactionsService.getBankList(), HttpStatus.OK);
    }

    @PostMapping("/nameValidation")
    public ResponseEntity<?> nameValidation(@RequestBody NameValidationModel nameValidationModel){
        return new ResponseEntity<>(transactionsService.validateName(nameValidationModel), HttpStatus.OK);
    }

    @PostMapping("doPayOut")
    public ResponseEntity<CustomReturnResponse> doPayOut(@RequestHeader (HttpHeaders.AUTHORIZATION) String authorization, @RequestBody PayoutDto payoutDto){
        String token = authorization.substring(7);
        return new ResponseEntity<>(transactionsService.doPayout(token, payoutDto), HttpStatus.OK);
    }

    @GetMapping("getInvestment")
    public ResponseEntity<InvestmentModel> getInvestment(@RequestHeader (HttpHeaders.AUTHORIZATION) String authorization){
        String token = authorization.substring(7);
        return new ResponseEntity<>(transactionsService.getInvestmentData(token),HttpStatus.OK);
    }

    @PostMapping("generatePaymentAccount")
    public  ResponseEntity<GenerateAccountResponse> generateAccount(@RequestBody GenerateAccountDto generateAccountDto){
        return new ResponseEntity<>(transactionsService.generateAccount(generateAccountDto),HttpStatus.OK);
    }


}
