package ru.tinkoff.edu.asavershin.hw4.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.asavershin.hw4.dto.*;
import ru.tinkoff.edu.asavershin.hw4.mappers.CarMapper;
import ru.tinkoff.edu.asavershin.hw4.services.CarService;

@RestController
@RequestMapping(path="/car")
@RequiredArgsConstructor
@Tag(name = "car", description = "Работа с машинами")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;


    @PostMapping
    @Operation(description = "Создание машины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseCar.class)) }),
            @ApiResponse(responseCode = "400", description = "Определенная ошибка валидации", content = {@Content()})
    })
    public ResponseCar createCar(@RequestBody @Valid RequestCar request){
        return carMapper.carToResponseCar(carService.createCar(carMapper.requestCarToCar(request)));
    }

//    @PutMapping
//    @Operation(description = "Обновление машины")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Ok", content =
//                    { @Content(mediaType = "application/json", schema =
//                    @Schema(implementation = ResponseCar.class)) }),
//            @ApiResponse(responseCode = "400", description = "Определенная ошибка валидации", content = {@Content()}),
//            @ApiResponse(responseCode = "404", description = "Машина с id carId не найдена", content = {@Content()})
//    })
//    public ResponseCar updateCar(@RequestBody @Valid RequestCar request){
//        return carMapper
//                .carToResponseCar(carService.updateCar(request.getId(), carMapper.requestCarToCar(request)));
//    }

    @DeleteMapping(path = "/{carId}")
    @Operation(description = "Удаление машины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {@Content()}),
            @ApiResponse(responseCode = "404", description = "Машина с id carId не найдена", content = {@Content()})
    })
    public void deleteCar(@PathVariable Long carId){
        carService.deleteCar(carId);
    }

    @GetMapping(path = "/{carId}")
    @Operation(description = "Получение машины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {@Content()}),
            @ApiResponse(responseCode = "404", description = "Машина с id carId не найдена", content = {@Content()})
    })
    public ResponseCar getCar(@PathVariable Long carId){
        return carMapper.carToResponseCar(carService.getCar(carId));
    }
}
