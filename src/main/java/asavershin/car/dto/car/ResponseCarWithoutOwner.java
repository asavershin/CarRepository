package asavershin.car.dto.car;

import asavershin.car.dto.autoservice.ResponseAutoservice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCarWithoutOwner {
    private Long id;

    private String releaseDate;

    private String color;

    private String model;

    private Long evp;

    private ResponseAutoservice autoservice;
}
