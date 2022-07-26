package com.example.demo.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

    @Documented
    @Constraint(validatedBy = NewUserDtoValidator.class)
    @Target( { ElementType.METHOD, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NewUserDtoConstraint {
        String message() default "Password must be longer than 8 characters and contain at one capitalized letter, one number and, one special character from this set '_,#,$,.'";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}
