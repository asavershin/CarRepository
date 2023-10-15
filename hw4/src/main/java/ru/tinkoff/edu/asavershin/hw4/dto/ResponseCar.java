package ru.tinkoff.edu.asavershin.hw4.dto;

import lombok.Getter;
import ru.tinkoff.edu.asavershin.hw4.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.utility.LocalDateTimeConverting;

@Getter
public class ResponseCar {

    private Long id;

    private String created;

    private String destroyed;

    private String color;

    private String model;

    public ResponseCar(Car car) {
        this.id = car.getId();
        this.created = LocalDateTimeConverting.localDateTimeToString(car.getCreated());
        this.destroyed = LocalDateTimeConverting.localDateTimeToString(car.getDestroyed());
        this.color = car.getColor();
        this.model = car.getModel().toString();
    }
}
