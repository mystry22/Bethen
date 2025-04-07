package com.bethen.bethen.dto;

import com.bethen.bethen.util.Role;
import lombok.Data;

@Data
public class AdminResponseDto {
    private String  adminId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
