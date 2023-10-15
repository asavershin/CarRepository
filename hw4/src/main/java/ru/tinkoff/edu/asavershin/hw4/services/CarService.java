package ru.tinkoff.edu.asavershin.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.CarDao;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarDao carDao;

    public Car createCar(Car car){
        return carDao.addCar(car);
    }

    public Car updateCar(long id, Car car){
        return carDao.updateById(id, car);
    }

    public void deleteCar(Long id) {
        carDao.deleteById(id);
    }

    public Car getCar(Long id) {
        return carDao.findById(id);
    }
}
