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

    private LocalDateTime createdAt;

    private LocalDateTime destroyedAt;

    private String color;

    private Model model;

    private Person owner;

}
