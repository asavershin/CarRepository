package ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
