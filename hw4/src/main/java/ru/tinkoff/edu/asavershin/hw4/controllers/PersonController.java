package ru.tinkoff.edu.asavershin.hw4.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.asavershin.hw4.dto.PersonRequestForCreate;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponsePerson;
import ru.tinkoff.edu.asavershin.hw4.mappers.PersonMapper;
import ru.tinkoff.edu.asavershin.hw4.services.PersonService;

@RestController
@RequestMapping(path="/person")
@RequiredArgsConstructor
@Tag(name = "person", description = "Работа с людьми")
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;
    @PostMapping
    @Operation(description = "Создание человека")
    public ResponsePerson createPerson(@Valid PersonRequestForCreate request){
        return personMapper
                .personToResponsePerson(personService.createPerson(personMapper.requestPersonToPerson(request)));
    }
    @DeleteMapping("/{personId}")
    @Operation(description = "Удаление человека")
    public void deletePerson(@PathVariable Long personId){
        personService.deleteById(personId);
    }

    @GetMapping("/{personId}")
    @Operation(description = "Найти человека по id")
    public ResponsePerson getPersonById (@PathVariable Long personId){
        return personMapper.personToResponsePerson(personService.findPersonById(personId));
    }

}
