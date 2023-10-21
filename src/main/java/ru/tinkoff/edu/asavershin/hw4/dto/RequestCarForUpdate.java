package ru.tinkoff.edu.asavershin.hw4.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.*;
import lombok.Getter;
import ru.tinkoff.edu.asavershin.hw4.validation.constraints.CorrectModel;
import ru.tinkoff.edu.asavershin.hw4.validation.constraints.LocalDateTimeFormat;

@Getter
public class RequestCarForUpdate {

    @NotBlank(message = "Не заполнен цвет")
    @Size(max = 20,message = "Слишком длинное название цвета")
    private String color;
    @Min(value = 1, message = "Id>=1")
    private Long personId;
    @Min(value = 1, message = "Id>=1")
    private Long autoserviceId;
}
