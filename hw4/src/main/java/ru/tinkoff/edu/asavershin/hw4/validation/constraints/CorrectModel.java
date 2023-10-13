package ru.tinkoff.edu.asavershin.hw4.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.tinkoff.edu.asavershin.hw4.validation.validators.CorrectModelValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CorrectModelValidator.class)
public @interface CorrectModel {
    String message() default "Марки машины нет";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
