package ru.tinkoff.edu.asavershin.hw4.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class Car {

    private Long id;

    private LocalDateTime created;

    private LocalDateTime destroyed;

    private String color;

    private Model model;

}
