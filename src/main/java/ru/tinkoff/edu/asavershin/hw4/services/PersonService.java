package ru.tinkoff.edu.asavershin.hw4.services;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.PersonRepository;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Integer age, String name){
        Person person = personRepository.findPersonById(id);
        if(person == null){
            throw new NotFoundException("Человека с id " + id + " не найдено");
        }
        person.setAge(age);
        person.setName(name);
        return personRepository.save(person);
    }

    public void deleteById(Long personId) {
        var person = personRepository.findPersonById(personId);
        if(person == null){
            throw new NotFoundException("Человека с id " + personId + " не найдено");
        }
        var cars = person.getCars();
        cars.forEach(car -> car.setOwner(null));
        personRepository.delete(person);
    }

    public Person  findPersonById(Long id){
        Person person = personRepository.findPersonById(id);
        if (person==null){
            throw new NotFoundException("Человека с id " + id + " не найдено");
        }
        return person;
    }

    public List<Person> findPeopleWithCarsFromCountryAndAmount(String country, Integer amount) {
        return personRepository.findPeopleWithCarsFromCountry(country, amount);
    }
}
