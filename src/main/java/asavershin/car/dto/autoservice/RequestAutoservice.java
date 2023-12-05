package asavershin.car.dto.autoservice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RequestAutoservice {
    @NotBlank(message = "У всего есть название, а у вас вдруг нет")
    @Size(max = 50, message = "Максимальная длина 50 символов")
    private String name;
    @NotBlank(message = "Адресс необходим")
    @Size(max = 50, message = "Максимальная длина 50 символов")
    private String address;
    @NotBlank(message = "У всех и вся есть Родина")
    @Size(max = 50, message = "Максимальная длина 50 символов")
    private String country;
}
