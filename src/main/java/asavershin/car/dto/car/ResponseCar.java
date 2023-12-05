package asavershin.car.dto.car;

import asavershin.car.dto.autoservice.ResponseAutoservice;
import asavershin.car.dto.person.ResponsePerson;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCar {

    private Long id;

    private String releaseDate;

    private String color;

    private String model;

    private Long evp;

    private String createdAt;

    private String lastUpdatedAt;

    private ResponsePerson owner;

    private ResponseAutoservice autoservice;
}
