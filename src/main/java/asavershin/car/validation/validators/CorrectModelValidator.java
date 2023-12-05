package asavershin.car.validation.validators;

import asavershin.car.dao.entities.Model;
import asavershin.car.validation.constraints.CorrectModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
