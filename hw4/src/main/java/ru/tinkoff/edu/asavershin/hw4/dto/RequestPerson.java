package ru.tinkoff.edu.asavershin.hw4.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RequestPerson{
    @NotBlank(message = "У всех есть имя, введите своё пж")
    @Size(max = 50, message = "Максимальная длина имени 50 символов")
    private String name;
    @NotNull(message = "Возраст обязан быть заполнен")
    @Max(value = 200, message = "Вы слишком стары")
    @Min(value = 18, message = "Вы слишком молоды для нашего сервиса")
    private Integer age;
}
