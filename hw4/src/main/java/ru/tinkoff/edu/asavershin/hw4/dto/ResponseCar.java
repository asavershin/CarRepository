package ru.tinkoff.edu.asavershin.hw4.dto;

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

    private ResponsePerson owner;

    private ResponseAutoservice autoservice;
}
