package com.bethen.bethen.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidatePhoneImplementation implements ConstraintValidator<ValidatePhoneNumber, String> {

    private static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
    @Override
    public void initialize(ValidatePhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null || input.isEmpty()) {
            return false; // Allow null or empty values if needed
        }
        return PHONE_NUMBER_PATTERN.matcher(input).matches() && input.length() == 14;
    }
}
