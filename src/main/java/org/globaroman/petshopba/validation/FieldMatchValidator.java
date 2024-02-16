package org.globaroman.petshopba.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ReflectionUtils;

@Log4j2
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field firstField = ReflectionUtils.findField(value.getClass(), firstFieldName);
            Field secondField = ReflectionUtils.findField(value.getClass(), secondFieldName);
            Object firstObj = getFieldValueByFieldName(firstField.getName(), value);
            Object secondObj = getFieldValueByFieldName(secondField.getName(), value);
            return Objects.equals(firstObj, secondObj);
        } catch (Exception e) {
            log.error("An error occurred while validating fields: {}", e.getMessage());
            log.debug("Stack trace: ", e);
            return false;
        }
    }

    private Object getFieldValueByFieldName(String name, Object value) {
        try {
            String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method method = value.getClass().getMethod(methodName);
            return method.invoke(value);
        } catch (Exception e) {
            throw new RuntimeException("Can't get access to field" + name, e);

        }
    }
}
