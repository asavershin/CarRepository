package asavershin.car.dto.person;

import asavershin.car.dto.car.RequestCar;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestPersonWithCars extends RequestPerson{
    @Valid
    List<RequestCar> cars;
}
