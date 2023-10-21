package ru.tinkoff.edu.asavershin.hw4.dto;

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
