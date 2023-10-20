package ru.tinkoff.edu.asavershin.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.CarRepository;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Car createCar(Car car){
        return carRepository.save(car);
    }

//    public Car updateCar(long id, Car car){
//        return carRepository.u(id, car);
//    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car getCar(Long id) {
        return carRepository.findCarById(id);
    }
}
