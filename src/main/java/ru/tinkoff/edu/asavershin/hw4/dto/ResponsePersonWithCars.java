package ru.tinkoff.edu.asavershin.hw4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;

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
