package ru.tinkoff.edu.asavershin.hw4.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestPerson;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponsePerson;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponsePersonWithCars;
import ru.tinkoff.edu.asavershin.hw4.mappers.PersonMapper;
import ru.tinkoff.edu.asavershin.hw4.services.PersonService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/person")
@RequiredArgsConstructor
@Tag(name = "person", description = "Работа с людьми")
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;
    @PostMapping
    @Operation(description = "Создание человека")
    public ResponsePerson createPerson(@RequestBody @Valid RequestPerson request){
        return personMapper
                .personToResponsePerson(personService.createPerson(personMapper.requestPersonToPerson(request)));
    }

    @PutMapping("/{personId}")
    @Operation(description = "Обновление человека")
    public ResponsePerson updatePerson(@PathVariable Long personId, @RequestBody @Valid RequestPerson request){
        return personMapper
                .personToResponsePerson(personService.
                        updatePerson(personId, request.getAge(), request.getName()));
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

    @GetMapping("/filter")
    public List<ResponsePersonWithCars> filterPeople(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer amount
    ) {
        return personService.findPeopleWithCarsFromCountryAndAmount(country, amount)
                .stream()
                .map(personMapper::personToResponsePersonWithCars)
                .collect(Collectors.toList());
    }

}
