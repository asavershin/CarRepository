package asavershin.car.dto.person;

import asavershin.car.dto.car.ResponseCarWithoutOwner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@Setter
@Getter
public class ResponsePersonWithCars {
    private Long id;
    private String personName;
    private Integer personAge;
    private Integer carsAmount;
    private List<ResponseCarWithoutOwner> cars;
}
