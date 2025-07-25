package com.winkstec.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "Password must contain upper, lower, digit and symbol, min 8 characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
