package asavershin.car.validation.constraints;

import asavershin.car.validation.validators.LocalDateTimeFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateTimeFormatValidator.class)
public @interface LocalDateTimeFormat {
    String message() default "Дата введена неверно";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
