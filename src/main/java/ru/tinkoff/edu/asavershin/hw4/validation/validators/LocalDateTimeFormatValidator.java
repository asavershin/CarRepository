package ru.tinkoff.edu.asavershin.hw4.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestCar;
import ru.tinkoff.edu.asavershin.hw4.utility.LocalDateTimeConverting;
import ru.tinkoff.edu.asavershin.hw4.validation.constraints.LocalDateTimeFormat;

import java.text.ParseException;
import java.time.LocalDateTime;

public class LocalDateTimeFormatValidator implements ConstraintValidator<LocalDateTimeFormat, RequestCar> {
    @Override
    public boolean isValid(RequestCar requestCar, ConstraintValidatorContext context) {

        LocalDateTime releaseDate = null;

        try {
            releaseDate = LocalDateTimeConverting.stringToLocalDateTime(requestCar.getReleaseDate());
        } catch (ParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Ошибка в дате создания").addConstraintViolation();
            return false;
        }

        return true;
    }
}
