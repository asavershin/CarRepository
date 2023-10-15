package ru.tinkoff.edu.asavershin.hw4.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestCar;
import ru.tinkoff.edu.asavershin.hw4.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.entities.Model;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.CarNotFoundException;
import ru.tinkoff.edu.asavershin.hw4.utility.LocalDateTimeConverting;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class CarLister {

    private final List<Car> cars;
    private Long carId;

    public CarLister() {
        cars = new LinkedList<>();
        carId = 1L;
    }

    public Car createCourse(RequestCar request){
        Car newCar = null;
        try {
            newCar = new Car((long)(carId++),
                                    LocalDateTimeConverting.stringToLocalDateTime(request.getCreated()),
                                    LocalDateTimeConverting.stringToLocalDateTime(request.getDestroyed()),
                                    request.getColor(),
                                    Model.valueOf(request.getModel())
                    );
        } catch (ParseException e) {
            throw new RuntimeException("Неизвестная ошибка");
        }
        cars.add(newCar);
        return  newCar;
    }

    public Car updateCar(RequestCar request){
        var updatedCar = cars.stream().filter(car -> Objects.equals(car.getId(), request.getId()))
                .findFirst().orElseThrow(() -> new CarNotFoundException("Машина с Id " + request.getId() + " не найдена"));
        updatedCar.setColor(request.getColor());
        updatedCar.setModel(Model.valueOf(request.getModel()));
        try {
            updatedCar.setCreated(LocalDateTimeConverting.stringToLocalDateTime(request.getCreated()));
            updatedCar.setDestroyed(LocalDateTimeConverting.stringToLocalDateTime(request.getDestroyed()));
        } catch (ParseException e) {
            throw new RuntimeException("Неизвестная ошибка");
        }
        return updatedCar;
    }

    public void deleteCar(Long id) {
        if (!cars.removeIf(car -> Objects.equals(car.getId(), id))) {
            throw new CarNotFoundException("Машина с Id " + id + " не найдена");
        }
    }

    public Car getCar(Long id) {
        return cars.stream().filter(car -> Objects.equals(car.getId(), id))
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException("Машина с Id " + id + " не найдена"));
    }
}
