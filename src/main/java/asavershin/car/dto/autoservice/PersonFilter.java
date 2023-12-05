package asavershin.car.dto.autoservice;

import asavershin.car.dto.PageDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonFilter extends PageDto {
    @Min(value = 0, message = "Меньше 0 не может быть машин")
    private Integer amount;
    @Size(max = 50, message = "Максимальная длина 50 символов")
    private String country;
}
