package ru.tinkoff.edu.asavershin.hw4.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.CarNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Car findById(long id){
        return cars.stream().filter(car -> Objects.equals(car.getId(), id))
                .findFirst()
                .orElseThrow(() -> new CarNotFoundException("Машина с Id " + id + " не найдена"));
    }

    public void deleteById(long id){
        if (!cars.removeIf(car -> Objects.equals(car.getId(), id))) {
            throw new CarNotFoundException("Машина с Id " + id + " не найдена");
        }
    }

    public Car updateById(long id, Car newCar){
        var car = findById(id);
        car.setColor(newCar.getColor());
        car.setModel(newCar.getModel());
        car.setCreatedAt(newCar.getCreatedAt());
        car.setDestroyedAt(newCar.getDestroyedAt());
        return car;
    }
}
