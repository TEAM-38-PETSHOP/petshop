package org.globaroman.petshopba.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final String PATTERN_OF_PHONE =
            "^(\\+\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone != null && Pattern.compile(PATTERN_OF_PHONE).matcher(phone).matches();
    }
}
