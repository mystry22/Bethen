package com.bethen.bethen.controllers;

import com.bethen.bethen.dto.*;
import com.bethen.bethen.msg.LoginSuccess;
import com.bethen.bethen.msg.Msg;
import com.bethen.bethen.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //Create new admin
    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String, List<String>> errorMap = new HashMap<>();

            for (FieldError error : errors) {
                String field = error.getField();
                String message = error.getDefaultMessage();
                errorMap.computeIfAbsent(field, k -> new java.util.ArrayList<>()).add(message);
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }else {
            String isCreated = adminService.createAdmin(memberRequestDto);

            if(isCreated.equals("new admin created")) {
                Msg msg = new Msg(true, "New admin created");
                return new ResponseEntity<Msg>(msg, HttpStatus.CREATED);
            }
            else {
                Msg msg = new Msg(false, "Admin already exist");
                return new ResponseEntity<Msg>(msg, HttpStatus.CREATED);
            }

        }
    }

    @GetMapping("/getAllAdmin")
    public ResponseEntity<List<MemberResponseDto>> getAllAdmin(){
        return new ResponseEntity<>(adminService.getAllAdmin(), HttpStatus.OK);
    }

    @PostMapping("/loginAdmin")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody LoginDto loginDto){
        String isAdmin = (String) adminService.loginAdmin(loginDto);

        if(isAdmin.equals("admin not validated")){
            Msg msg = new Msg(false, "Invalid credentials");
            return  new ResponseEntity<>(msg, HttpStatus.UNAUTHORIZED);
        }
        LoginSuccess loginSuccess = new LoginSuccess(true,isAdmin);
        return  new ResponseEntity<>(loginSuccess, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<?>deleteAdmin(@PathVariable String id){
        String isDeleted = adminService.deleteAdmin(id);

        if(isDeleted.equals("user does not exist")){
            Msg msg = new Msg(false, "Admin does not exist");
            return  new ResponseEntity<>(msg,HttpStatus.OK);
        }else {
            Msg msg = new Msg(true, "Admin deleted");
            return  new ResponseEntity<>(msg,HttpStatus.OK);
        }
    }

    //Suspend account
}
