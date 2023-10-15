package ru.tinkoff.edu.asavershin.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.CarDao;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarLister {

    private final CarDao carDao;

    public Car createCar(Car car){
        return carDao.addCar(car);
    }
//
//    public Car updateCar(RequestCar request){
//        var updatedCar = cars.stream().filter(car -> Objects.equals(car.getId(), request.getId()))
//                .findFirst().orElseThrow(() -> new CarNotFoundException("Машина с Id " + request.getId() + " не найдена"));
//        updatedCar.setColor(request.getColor());
//        updatedCar.setModel(Model.valueOf(request.getModel()));
//        try {
//            updatedCar.setCreated(LocalDateTimeConverting.stringToLocalDateTime(request.getCreated()));
//            updatedCar.setDestroyed(LocalDateTimeConverting.stringToLocalDateTime(request.getDestroyed()));
//        } catch (ParseException e) {
//            throw new RuntimeException("Неизвестная ошибка");
//        }
//        return updatedCar;
//    }
//
//    public void deleteCar(Long id) {
//        if (!cars.removeIf(car -> Objects.equals(car.getId(), id))) {
//            throw new CarNotFoundException("Машина с Id " + id + " не найдена");
//        }
//    }
//
//    public Car getCar(Long id) {
//        return cars.stream().filter(car -> Objects.equals(car.getId(), id))
//                .findFirst()
//                .orElseThrow(() -> new CarNotFoundException("Машина с Id " + id + " не найдена"));
//    }
}
