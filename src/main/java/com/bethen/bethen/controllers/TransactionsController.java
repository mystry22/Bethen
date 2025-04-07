package com.bethen.bethen.controllers;

import com.bethen.bethen.dto.ActivatePlanRequestDto;
import com.bethen.bethen.dto.PaymentLinkRequestDto;
import com.bethen.bethen.dto.WebhookResponseDto;
import com.bethen.bethen.dto.post.PaymentResponse;
import com.bethen.bethen.models.TransactionsModel;
import com.bethen.bethen.msg.Msg;
import com.bethen.bethen.services.HttpServices;
import com.bethen.bethen.services.TransactionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;


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
}
