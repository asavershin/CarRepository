package asavershin.car.controllers;

import asavershin.car.dto.PageResponse;
import asavershin.car.dto.car.CarFilter;
import asavershin.car.dto.car.RequestCar;
import asavershin.car.dto.car.RequestCarForUpdate;
import asavershin.car.dto.car.ResponseCar;
import asavershin.car.mappers.CarMapper;
import asavershin.car.services.CarService;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping(path="/car")
@RequiredArgsConstructor
@Tag(name = "car", description = "Работа с машинами")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;


    @PostMapping
    @Operation(description = "Создание машины(изначально не привязана к салону и человеку, добавляется впоследствии)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseCar.class)) }),
            @ApiResponse(responseCode = "400", description = "Определенная ошибка валидации", content = {@Content()})
    })
    public ResponseCar createCar(@RequestBody @Valid RequestCar request){
        return carMapper.carToResponseCar(carService.createCar(carMapper.requestCarToCar(request)));
    }

    @PutMapping("/{carId}")
    @Operation(description = "Обновление машины")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseCar.class)) }),
            @ApiResponse(responseCode = "400", description = "Определенная ошибка валидации", content = {@Content()}),
            @ApiResponse(responseCode = "404", description = "Машина с id carId не найдена", content = {@Content()})
    })
    public ResponseCar updateCar(@PathVariable Long carId, @RequestBody @Valid RequestCarForUpdate request){
        return carMapper
                .carToResponseCar(carService.updateCar(carId,
                        request.getColor(), request.getPersonId(), request.getAutoserviceId()));
    }

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
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseCar.class)) }),
            @ApiResponse(responseCode = "404", description = "Машина с id carId не найдена", content = {@Content()})
    })
    public ResponseCar getCar(@PathVariable Long carId){
        return carMapper.carToResponseCar(carService.getCarById(carId));
    }


    @Operation(description = "Получение машин по фильтру: возраст водителя больше указанного, страна, цвет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Машина с id carId не найдена", content = {@Content()})
    })
    @GetMapping("/filter")
    public PageResponse<ResponseCar> filterCars(@Valid CarFilter filter) {
        return carMapper.pageResponseWithResponseCars(
                carService.filterCarsByAgeCountryAndColor(
                        filter.getPageNumber(), filter.getPageSize(), filter.toPredicate()));
    }
    @Operation(description = "Получение всех машин определенного человека")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = List.class,
                            subTypes = ResponseCar.class))
            }),
            @ApiResponse(responseCode = "404", description = "Человек с id personId не найден", content = {@Content()})
    })
    @GetMapping("/bypersonid/{personId}")
    public List<ResponseCar> findCarsByPersonId(@PathVariable Long personId) {
        return carService.findCarsByPersonId(personId).stream()
                .map(carMapper::carToResponseCar)
                .collect(Collectors.toList());
    }

    @Operation(description = "Получение машины по электронному паспорту(evp)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseCar.class)) }),
            @ApiResponse(responseCode = "404", description = "Машина с evp не найдена", content = {@Content()})
    })
    @GetMapping("/byevp/{evp}")
    public ResponseCar findCarByEvp(@PathVariable Long evp){
        return carMapper.carToResponseCar(carService.findCarByEvp(evp));
    }
}
