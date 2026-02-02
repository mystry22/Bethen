package com.bethen.bethen.controllers;

import com.bethen.bethen.dto.ChangePasswordRequestDto;
import com.bethen.bethen.dto.LoginDto;
import com.bethen.bethen.dto.MemberRequestDto;
import com.bethen.bethen.dto.MemberResponseDto;
import com.bethen.bethen.models.MemberModel;
import com.bethen.bethen.msg.LoginSuccess;
import com.bethen.bethen.msg.Msg;
import com.bethen.bethen.services.MemberService;
import com.bethen.bethen.util.Helper;
import com.bethen.bethen.util.JwtUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;


import javax.crypto.SecretKey;

import java.security.SecureRandom;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST, RequestMethod.OPTIONS})
@RequestMapping("/api/v1/members")
public class MembersController {


    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtUtil jwtUtil;

    //Get all members
    @GetMapping("/getAllMembers")
    public ResponseEntity<List<MemberResponseDto>> getAllMembers(){

        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    //Get member by ID
    @GetMapping("/getMemberById/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable String id){
        MemberResponseDto memberResponseDto = memberService.getMemberById(id);

        if (memberResponseDto == null){
            Msg msg = new Msg(false, "No user found");
            return  new  ResponseEntity<Msg>(msg,HttpStatus.NOT_FOUND);
        }

        return  new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    //Create new member
    @PostMapping("/createNewMember")
    public ResponseEntity<?> createMember(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, List<String>> errorMap = new HashMap<>();

            for(FieldError error : errors){
                String field = error.getField();
                String message = error.getDefaultMessage();
                errorMap.computeIfAbsent(field, k -> new  java.util.ArrayList<>()).add(message);
            }

            return  new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }else {
            Object memberModel = memberService.createNewmember(memberRequestDto);
            if (memberModel == null ) {

                Msg mes = new Msg(false, "Unable to create user, please confirm user does not exist");
                return new ResponseEntity<Msg>(mes, HttpStatus.OK);
            }
            Msg mes = new Msg(true, "New user created");
            return new ResponseEntity<>(mes, HttpStatus.CREATED);

        }

    }

    //Delete member by ID
    @DeleteMapping("/deleteMember/{id}")
    public ResponseEntity<Msg> deleteMember(@PathVariable String id){
        String res = memberService.deleteMember(id);
        
        if(Objects.equals(res, "deleted")){
            Msg msg = new Msg(true, "User deleted");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        Msg msg = new Msg(false, "User does not exist");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    //Change password
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, List<String>> errorMap = new HashMap<>();

            for(FieldError error : errors){
                String field = error.getField();
                String message = error.getDefaultMessage();
                errorMap.computeIfAbsent(field, k -> new  java.util.ArrayList<>()).add(message);
            }

            return  new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }else {

            String response = memberService.changePassword(changePasswordRequestDto);
            if(Objects.equals(response, "user password updated")){
                Msg msg = new Msg(true, "Password updated");
                return  new ResponseEntity<>(msg, HttpStatus.OK);
            }else {
                Msg msg = new Msg(false, "User does not exist");
                return  new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
            }
        }
    }

    //Trying to force a deployment
    //Login members

    @PostMapping("/memberLogin")
    public ResponseEntity<?> memberLogin(@RequestBody LoginDto loginDto,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, List<String>> errorMap = new HashMap<>();

            for(FieldError error : errors){
                String field = error.getField();
                String message = error.getDefaultMessage();
                errorMap.computeIfAbsent(field, k -> new  java.util.ArrayList<>()).add(message);
            }

            return  new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }else {
             Object isUser = memberService.loginUser(loginDto);
             if(isUser == "wrong credential"){
                 Msg msg = new Msg(false, "Invalid credentials");
                 return new ResponseEntity<>(msg, HttpStatus.UNAUTHORIZED);
             }else {
                 LoginSuccess loginSuccess = new LoginSuccess(true, (String) isUser);
                 return  new ResponseEntity<>(loginSuccess,HttpStatus.OK);
             }
        }
    }

    //Generate secret
    @GetMapping("/secret")
    public String generateSeceret(){
        //return Helper.generateReference();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits for HS256, adjust as needed
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);



    }

    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){

        String token = authorizationHeader.substring(7);
        return  new ResponseEntity<>(memberService.getUserDetailsViaToken(token), HttpStatus.OK);
    }
}
