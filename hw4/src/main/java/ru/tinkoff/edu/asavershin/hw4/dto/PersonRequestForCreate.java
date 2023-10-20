package ru.tinkoff.edu.asavershin.hw4.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PersonRequestForCreate implements PersonRequest {
    @NotNull(message = "У всех есть имени, введите своё пж")
    @Size(max = 50, message = "Максимальная длина имени 50 символов")
    private String name;
    @NotNull(message = "Возраст обязан быть заполнен")
    @Max(value = 200, message = "Вы слишком стары")
    @Min(value = 18, message = "Вы слишком молоды для нашего сервиса")
    private Integer age;
}
