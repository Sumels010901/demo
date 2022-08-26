package com.example.constraint;
import com.example.validation.NameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
    String message() default "Invalid name";
    Class<?>[] groups() default {};
    boolean allowNull() default false;
    Class<? extends Payload>[] payload() default{};
}
