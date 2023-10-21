package ru.tinkoff.edu.asavershin.hw4.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.tinkoff.edu.asavershin.hw4.validation.constraints.CorrectModel;
import ru.tinkoff.edu.asavershin.hw4.validation.constraints.LocalDateTimeFormat;

@Getter
@LocalDateTimeFormat
public class RequestCar {

    @Size(max = 100, message = "Слишком длинная дата")
    @JsonAlias({"createdAt", "created_at"})
    private String releaseDate;

    @NotBlank(message = "Не заполнен цвет")
    @Size(max = 20,message = "Слишком длинное название цвета")
    private String color;

    @CorrectModel
    private String model;

    @NotNull
    private Long evp;
}
