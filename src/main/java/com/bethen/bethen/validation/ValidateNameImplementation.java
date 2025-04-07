package com.bethen.bethen.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class ValidateNameImplementation implements ConstraintValidator<ValidateTextChars, String> {
    @Override
    public void initialize(ValidateTextChars constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null || input.isEmpty() ) {
            return false; // Allow null or empty values
        }
        return input.matches("[a-zA-Z]+");
    }
}
