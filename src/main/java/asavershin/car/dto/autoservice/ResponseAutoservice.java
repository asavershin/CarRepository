package asavershin.car.dto.autoservice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseAutoservice {
    private Long id;
    private String name;
    private String address;
    private String country;

}
