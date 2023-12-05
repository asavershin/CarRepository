package asavershin.car.dto.person;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponsePerson {
    private Long id;
    private String personName;
    private Integer personAge;
}
