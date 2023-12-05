package asavershin.car.validation.validators;

import asavershin.car.dto.car.RequestCar;
import asavershin.car.utility.LocalDateTimeConverting;
import asavershin.car.validation.constraints.LocalDateTimeFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class LocalDateTimeFormatValidator implements ConstraintValidator<LocalDateTimeFormat, RequestCar> {
    @Override
    public boolean isValid(RequestCar requestCar, ConstraintValidatorContext context) {

        LocalDateTime releaseDate = null;

        try {
            releaseDate = LocalDateTimeConverting.stringToLocalDateTime(requestCar.getReleaseDate());
        } catch (Exception e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Ошибка в дате создания").addConstraintViolation();
            return false;
        }

        return true;
    }
}
