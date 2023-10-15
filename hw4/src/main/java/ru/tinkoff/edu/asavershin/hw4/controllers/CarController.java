package ru.tinkoff.edu.asavershin.hw4.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.asavershin.hw4.dto.*;
import ru.tinkoff.edu.asavershin.hw4.mappers.CarMapper;
import ru.tinkoff.edu.asavershin.hw4.services.CarLister;

@RestController
@RequestMapping(path="/car")
@RequiredArgsConstructor
public class CarController {

    private final CarLister carLister;
    private final CarMapper carMapper;


    @PostMapping
    public ResponseCar createCar(@RequestBody @Valid RequestCar request){
        return carMapper.carToResponseCar(carLister.createCar(carMapper.requestCarToCar(request)));

//        carLister.createCar(carMapper.requestCarToCar(request));

    }
//
//    @PutMapping
//    public ResponseCar updateCar(@RequestBody @Valid RequestCar request){
//        return new ResponseCar(carLister.updateCar(request));
//    }
//
//    @DeleteMapping(path = "/{carId}")
//    public void deleteCar(@PathVariable Long carId){
//        carLister.deleteCar(carId);
//    }
//
//    @GetMapping(path = "/{carId}")
//    public ResponseCar updateCar(@PathVariable Long carId){
//        return new ResponseCar(carLister.getCar(carId));
//    }
}
