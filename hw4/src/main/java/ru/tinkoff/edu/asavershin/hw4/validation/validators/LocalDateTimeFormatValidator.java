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

        LocalDateTime localStartDate = null;
        LocalDateTime localEndDate = null;
        boolean isValid = true;

        try {
            localStartDate = LocalDateTimeConverting.stringToLocalDateTime(requestCar.getCreated());
        } catch (ParseException e) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Ошибка в дате создания").addConstraintViolation();
        }

        try {
            localEndDate = LocalDateTimeConverting.stringToLocalDateTime(requestCar.getDestroyed());
        } catch (ParseException e) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Ошибка в дате конца").addConstraintViolation();
        }

        if (localStartDate != null && localEndDate != null && localEndDate.isBefore(localStartDate)) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Дата конца должна быть позже даты создания").addConstraintViolation();
        }

        return isValid;
    }
}
