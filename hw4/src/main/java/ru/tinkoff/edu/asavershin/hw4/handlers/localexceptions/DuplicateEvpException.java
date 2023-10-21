package ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions;

public class DuplicateEvpException extends RuntimeException {
    public DuplicateEvpException(String message) {
        super(message);
    }
}
