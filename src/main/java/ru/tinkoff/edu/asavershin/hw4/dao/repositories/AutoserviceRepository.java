package ru.tinkoff.edu.asavershin.hw4.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Autoservice;

public interface AutoserviceRepository extends JpaRepository<Autoservice, Long> {
    Autoservice findAutoserviceById(Long autoserviceId);

}
