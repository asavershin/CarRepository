package ru.tinkoff.edu.asavershin.hw4.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestPerson;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseCar;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponsePerson.class)) }),
            @ApiResponse(responseCode = "400", description = "Определенная ошибка валидации", content = {@Content()}),
    })
    @Operation(description = "Создание человека")
    public ResponsePerson createPerson(@RequestBody @Valid RequestPerson request){
        return personMapper
                .personToResponsePerson(personService.createPerson(personMapper.requestPersonToPerson(request)));
    }

    @PutMapping("/{personId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponsePerson.class)) }),
            @ApiResponse(responseCode = "400", description = "Определенная ошибка валидации", content = {@Content()}),
            @ApiResponse(responseCode = "404", description = "Человек с id personId не найден", content = {@Content()})
    })
    @Operation(description = "Обновление человека")
    public ResponsePerson updatePerson(@PathVariable Long personId, @RequestBody @Valid RequestPerson request){
        return personMapper
                .personToResponsePerson(personService.
                        updatePerson(personId, request.getAge(), request.getName()));
    }

    @DeleteMapping("/{personId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {}),
            @ApiResponse(responseCode = "404", description = "Человек с id personId не найден", content = {@Content()})
    })
    @Operation(description = "Удаление человека")
    public void deletePerson(@PathVariable Long personId){
        personService.deleteById(personId);
    }

    @GetMapping("/{personId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponsePerson.class)) }),
            @ApiResponse(responseCode = "404", description = "Человек с id personId не найден", content = {@Content()})
    })
    @Operation(description = "Найти человека по id")
    public ResponsePerson getPersonById (@PathVariable Long personId){
        return personMapper.personToResponsePerson(personService.findPersonById(personId));
    }

    @GetMapping("/filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = List.class,
                    subTypes = ResponsePersonWithCars.class))}),
    })
    @Operation(description = "Фильтр людей у который больше amount машин из страны cpuntry")
    public List<ResponsePersonWithCars> filterPeople(String country, Integer amount){
        return personService.findPeopleWithCarsFromCountryAndAmount(country, amount)
                .stream()
                .map(personMapper::personToResponsePersonWithCars)
                .collect(Collectors.toList());
    }

}
