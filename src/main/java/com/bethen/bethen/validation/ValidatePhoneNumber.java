package com.bethen.bethen.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented()
@Constraint(validatedBy = ValidatePhoneImplementation.class)
public @interface ValidatePhoneNumber {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
