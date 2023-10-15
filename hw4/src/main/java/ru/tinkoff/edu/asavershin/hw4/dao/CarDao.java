package ru.tinkoff.edu.asavershin.hw4.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarDao {
    private final List<Car> cars = new ArrayList<>();
    private Long carId = 1L;
    private final Person owner;

    public Car addCar(Car car){
        car.setId(carId++);
        car.setOwner(owner);
        cars.add(car);
        return car;
    }
}
