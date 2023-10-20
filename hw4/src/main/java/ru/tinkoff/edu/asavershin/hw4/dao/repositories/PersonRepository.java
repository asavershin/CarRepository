package ru.tinkoff.edu.asavershin.hw4.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonById(Long id);
}
