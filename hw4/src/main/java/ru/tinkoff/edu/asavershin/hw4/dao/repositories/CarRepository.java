package ru.tinkoff.edu.asavershin.hw4.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
//    int updateCarModelById(@Param("model") String model, @Param("carId") Long carId);
    public Car findCarById(Long id);
}
