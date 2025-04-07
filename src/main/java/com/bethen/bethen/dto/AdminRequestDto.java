package com.bethen.bethen.dto;

import com.bethen.bethen.validation.ValidateTextChars;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AdminRequestDto {

    @ValidateTextChars(message = "First name can only accept text chars")
    @Size(min = 3, max = 20, message = "First name can only accept a min of 3 and max 20 text chars")
    private String firstName;

    @ValidateTextChars(message = "Last name can only accept text chars")
    @Size(min = 3, max = 20, message = "Last name can only accept a min of 3 and max 20 text chars")
    private String lastName;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password length cannot be less than 8 text chars")
    private String password;

    @NotNull(message = "Email field cannot be empty")
    @Email(message = "Please provide a valid email address")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
