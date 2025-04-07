package com.bethen.bethen.validation;

import com.bethen.bethen.util.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValidateCurrencyImplementation implements ConstraintValidator<ValidateCurrency, String> {
    private Set<String> validCurrencies;
    @Override
    public void initialize(ValidateCurrency constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        validCurrencies = new HashSet<>(Arrays.asList(Currency.NGN.name(), Currency.USD.name(), Currency.GBP.name(),Currency.KES.name(),Currency.GHS.name()));
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {

        if(input.isEmpty()){
            return false;
        }

        return validCurrencies.contains(input);
    }
}
