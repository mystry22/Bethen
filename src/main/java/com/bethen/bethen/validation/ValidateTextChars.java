package com.bethen.bethen.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint( validatedBy = ValidateNameImplementation.class)
@Documented()
public @interface ValidateTextChars {

    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
