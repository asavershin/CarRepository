package asavershin.car.services;

import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Person;
import asavershin.car.dao.repositories.CustomPersonRepository;
import asavershin.car.dao.repositories.PersonRepository;
import asavershin.car.dto.autoservice.PersonFilter;
import asavershin.car.handlers.localexceptions.DuplicateEvpException;
import asavershin.car.handlers.localexceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final CustomPersonRepository customPersonRepository;

    public Person createPerson(Person person){
        return personRepository.save(person);
    }

    @Transactional
    public Person updatePerson(Long id, Integer age, String name){
        Person person = personRepository.findPersonById(id);
        if(person == null){
            throw new NotFoundException("Человека с id " + id + " не найдено");
        }
        person.setAge(age);
        person.setName(name);
        return personRepository.save(person);
    }

    @Transactional
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

    public List<Person> findPeopleWithCarsFromCountryAndAmount(int pageNumber, int pageSize, PersonFilter filter) {
        var cars = customPersonRepository.findCarOfPeopleWithCarsFromCountry(pageNumber, pageSize, filter).toList();
        var carsByOwner = cars.stream()
                .collect(Collectors.groupingBy(Car::getOwner));

        List<Person> people = new ArrayList<>();

        for (var entry : carsByOwner.entrySet()) {
            Person owner = entry.getKey();
            List<Car> ownerCars = entry.getValue();
            owner.setCars(ownerCars);
            people.add(owner);
        }

        return people;
    }

    @Transactional
    public Person createPersonWithCars(Person person){
        person.getCars().forEach(car -> car.setOwner(person));
        try {
            return personRepository.saveAndFlush(person);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicateEvpException("Одна или более из машин с evp "+ person.getCars().stream()
                    .map(Car::getEvp)
                    .map(String::valueOf).collect(Collectors.joining(", "))
            +" уже есть");
        }
    }
}
