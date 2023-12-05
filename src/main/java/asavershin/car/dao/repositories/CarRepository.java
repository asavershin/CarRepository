package asavershin.car.dao.repositories;

import asavershin.car.dao.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long>, QuerydslPredicateExecutor<Car> {
    public Car findCarById(Long id);
    @Query("SELECT c FROM Car c JOIN FETCH c.owner JOIN FETCH c.autoservice WHERE " +
            "(:age IS NULL OR c.owner.age > :age) " +
            "AND (:country IS NULL OR LOWER(c.autoservice.country) = LOWER(:country)) " +
            "AND (:color IS NULL OR LOWER(c.color) = LOWER(:color))")
    List<Car> filterCarsByAgeCountryAndColor(Integer age, String country, String color);

    @Query("SELECT c FROM Car c " +
            "JOIN FETCH c.owner p " +
            "JOIN FETCH c.autoservice a " +
            "WHERE (:country IS NULL OR LOWER(a.country) = LOWER(:country)) " +
            "AND (:amount IS NULL OR p.id IN (" +
            "    SELECT c.owner.id " +
            "    FROM Car c " +
            "    WHERE c.autoservice.id IN (" +
            "        SELECT a.id " +
            "        FROM Autoservice a " +
            "        WHERE LOWER(a.country) = LOWER(:country)" +
            "    )" +
            "    GROUP BY c.owner.id " +
            "    HAVING COUNT(c.id) > :amount" +
            "))")
    List<Car> findCarOfPeopleWithCarsFromCountry(@Param("country") String country, @Param("amount") Integer amount);

    public List<Car> findCarByOwnerId(Long personId);
    public Car findCarByEvp(Long evp);

    boolean existsByEvp(Long evp);

    @Query("UPDATE Car c " +
            "SET c.color = :color, c.owner.id = :personId, c.autoservice.id = :autoserviceId " +
            "WHERE c.id = :id")
    void updateCar(Long id, String color, Long personId, Long autoserviceId);


}
