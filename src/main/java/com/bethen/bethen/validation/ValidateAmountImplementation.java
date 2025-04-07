package com.bethen.bethen.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidateAmountImplementation implements ConstraintValidator<ValidateAmount, String> {

    private static final Pattern NUMBER_OR_DECIMAL_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    @Override
    public void initialize(ValidateAmount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {

        if(input.isEmpty() || input.trim().isEmpty()){
            return false;
        }

        return  NUMBER_OR_DECIMAL_PATTERN.matcher(input).matches();
    }
}
