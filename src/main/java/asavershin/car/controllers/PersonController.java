package asavershin.car.controllers;

import asavershin.car.dto.autoservice.PersonFilter;
import asavershin.car.dto.person.RequestPerson;
import asavershin.car.dto.person.RequestPersonWithCars;
import asavershin.car.dto.person.ResponsePerson;
import asavershin.car.dto.person.ResponsePersonWithCars;
import asavershin.car.mappers.CarMapper;
import asavershin.car.mappers.PersonMapper;
import asavershin.car.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/person")
@RequiredArgsConstructor
@Tag(name = "person", description = "Работа с людьми")
public class PersonController {
    private final PersonService personService;
    private final PersonMapper personMapper;
    private final CarMapper carMapper;

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
    @Operation(description = "Фильтр людей у который больше amount машин из страны country")
    public List<ResponsePersonWithCars> filterPeople(PersonFilter filter){
        return personMapper.pageResponseWithResponsePersonWithCars(personService.findPeopleWithCarsFromCountryAndAmount(filter.getPageNumber(), filter.getPageSize(), filter));
    }

    @PostMapping("person-with-cars")
    @Operation(description = "Создание человека с машинами")
    public ResponsePersonWithCars createPersonWithCars(@Valid@RequestBody RequestPersonWithCars request){
        return personMapper.personToResponsePersonWithCars(personService.createPersonWithCars(carMapper.personWithCars(request)));
    }
}
