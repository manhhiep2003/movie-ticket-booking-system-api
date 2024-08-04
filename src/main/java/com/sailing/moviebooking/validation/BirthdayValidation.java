package com.sailing.moviebooking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { BirthdayValidator.class })
public @interface BirthdayValidation {
    String message() default "Invalid day of birth";
    int min();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
