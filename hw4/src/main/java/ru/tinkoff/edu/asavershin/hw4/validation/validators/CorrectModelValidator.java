package ru.tinkoff.edu.asavershin.hw4.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tinkoff.edu.asavershin.hw4.entities.Model;
import ru.tinkoff.edu.asavershin.hw4.validation.constraints.CorrectModel;

public class CorrectModelValidator implements ConstraintValidator<CorrectModel, String> {
    @Override
    public boolean isValid(String modelString, ConstraintValidatorContext constraintValidatorContext) {
        try {
            var model = Model.valueOf(modelString);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
