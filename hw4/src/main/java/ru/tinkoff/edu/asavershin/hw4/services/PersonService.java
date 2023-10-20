package ru.tinkoff.edu.asavershin.hw4.services;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    public void deleteById(Long personId) {
        personRepository.deleteById(personId);
    }

    public Person  findPersonById(Long id){
        return personRepository.findPersonById(id);
    }

    public List<Person> findPeopleWithCarsFromCountryAndAmount(String country, Integer amount) {
        return personRepository.findPeopleWithCarsFromCountry(country, amount);
    }
}
