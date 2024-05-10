package org.globaroman.petshopba.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private static final String PATTERN_OF_PHONE =
            "^[\\+]?3?[\\s]?8?[\\s]?\\(?0\\d{2}?\\)?[\\s]?\\d{3}[\\s|-]?\\d{2}[\\s|-]?\\d{2}$";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone != null && Pattern.compile(PATTERN_OF_PHONE).matcher(phone).matches();
    }
}
