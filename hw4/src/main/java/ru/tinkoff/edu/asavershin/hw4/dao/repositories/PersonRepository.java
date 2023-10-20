package ru.tinkoff.edu.asavershin.hw4.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonById(Long id);

    @Query("SELECT p FROM Person p " +
            "JOIN p.cars c " +
            "WHERE (:country IS NULL OR LOWER(c.autoservice.country) = LOWER(:country)) " +
            "GROUP BY p HAVING (:amount IS NULL OR COUNT(c) > :amount)")
    List<Person> findPeopleWithCarsFromCountry(String country, Integer amount);
}
