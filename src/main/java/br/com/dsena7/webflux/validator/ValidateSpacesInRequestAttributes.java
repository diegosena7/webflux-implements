package br.com.dsena7.webflux.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {ValidateRequestAttributes.class})
@Target(ElementType.FIELD)
@Retention(RUNTIME)
public @interface ValidateSpacesInRequestAttributes {

    String message() default "Invalid name, field cannot have blank spaces at the beginning or end of the name";

    Class<?> [] groups() default{};

    Class<? extends Payload> [] payload() default{};
}
