package asavershin.car.dto.car;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class RequestCarForUpdate {

    @NotBlank(message = "Не заполнен цвет")
    @Size(max = 20,message = "Слишком длинное название цвета")
    private String color;
    @Min(value = 1, message = "Id>=1")
    @NotNull(message = "Не установлен владелец")
    private Long personId;
    @Min(value = 1, message = "Id>=1")
    @NotNull(message = "Не установлен автосервис")
    private Long autoserviceId;
}
