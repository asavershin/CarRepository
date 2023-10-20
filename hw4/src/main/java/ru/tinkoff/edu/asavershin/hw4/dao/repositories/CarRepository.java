package ru.tinkoff.edu.asavershin.hw4.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
//    int updateCarModelById(@Param("model") String model, @Param("carId") Long carId);
    public Car findCarById(Long id);
    @Query("SELECT c FROM Car c WHERE " +
            "(:age IS NULL OR c.owner.age > :age) " +
            "AND (:country IS NULL OR LOWER(c.autoservice.country) = LOWER(:country)) " +
            "AND (:color IS NULL OR LOWER(c.color) = LOWER(:color))")
    List<Car> filterCarsByAgeCountryAndColor(Integer age, String country, String color);
}
