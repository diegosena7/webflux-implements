package br.com.dsena7.webflux.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateRequestAttributes implements ConstraintValidator<ValidateSpacesInRequestAttributes, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return value.trim().length() == value.length();
    }
}
