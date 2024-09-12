package org.globaroman.petshopba.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Invalid format phone";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
