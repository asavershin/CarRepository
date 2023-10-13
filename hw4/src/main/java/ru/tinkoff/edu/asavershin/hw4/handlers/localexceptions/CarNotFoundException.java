package ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message) {
        super(message);
    }
}
