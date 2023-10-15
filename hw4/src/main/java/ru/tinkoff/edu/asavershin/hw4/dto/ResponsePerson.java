package ru.tinkoff.edu.asavershin.hw4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponsePerson {
    private String personName;
    private Integer personAge;
}
