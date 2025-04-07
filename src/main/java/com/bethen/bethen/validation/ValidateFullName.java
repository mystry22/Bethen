package com.bethen.bethen.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = ValidateFullNameImplemntation.class)
@Documented()
public @interface ValidateFullName {

    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
