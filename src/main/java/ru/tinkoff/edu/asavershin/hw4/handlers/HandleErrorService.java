package ru.tinkoff.edu.asavershin.hw4.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.DuplicateEvpException;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class HandleErrorService {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("errors",
                ex.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).filter(Objects::nonNull)
                        .toList());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleCarNotFoundException(NotFoundException ex){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("resultCode", HttpStatus.NOT_FOUND.value());
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEvpException.class)
    public ResponseEntity<Object> handleDuplicateEvpException(DuplicateEvpException ex){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("evp:", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
