package com.bethen.bethen.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidateFullNameImplemntation implements ConstraintValidator<ValidateFullName, String> {
    private static final Pattern FULL_NAME_PATTERN = Pattern.compile("^\\S+\\s+\\S+.*$");
    @Override
    public void initialize(ValidateFullName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null || input.isEmpty() ) {
            return false; // Allow null or empty values
        }
        return FULL_NAME_PATTERN.matcher(input).matches();
    }
}
