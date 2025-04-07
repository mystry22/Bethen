package com.bethen.bethen.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateCurrencyImplementation.class)
@Documented()
public @interface ValidateCurrency {

    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
