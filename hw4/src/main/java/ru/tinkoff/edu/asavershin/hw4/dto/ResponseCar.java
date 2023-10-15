package ru.tinkoff.edu.asavershin.hw4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCar {

    private Long id;

    private String created;

    private String destroyed;

    private String color;

    private String model;

    private ResponsePerson responsePerson;
}
