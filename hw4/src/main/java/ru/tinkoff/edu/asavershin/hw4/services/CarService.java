package ru.tinkoff.edu.asavershin.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Autoservice;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.AutoserviceRepository;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.CarRepository;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.PersonRepository;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.DuplicateEvpException;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final AutoserviceRepository autoserviceRepository;

    public Car createCar(Car car){
        if (carRepository.existsByEvp(car.getEvp())) {
            throw new DuplicateEvpException("Такой EVP "+ car.getEvp() + " уже есть");
        }
        return carRepository.save(car);
    }

    public Car updateCar(long id, String color, Long personId, Long autoserviceId){
        Car car = carRepository.findCarById(id);
        if(car==null){
            throw new NotFoundException("Машины с Id " + id + " не найдено");
        }
        Person person = personRepository.findPersonById(personId);
        if(person == null && personId != null){
            throw new NotFoundException("Человека с Id " + personId + " не найдено");
        }
        Autoservice autoservice = autoserviceRepository.findAutoserviceById(autoserviceId);
        if(autoservice==null && autoserviceId != null){
            throw new NotFoundException("Автосервиса с Id " + autoserviceId + " не найдено");
        }

        car.setColor(color);
        car.setOwner(person);
        car.setAutoservice(autoservice);

        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car getCarById(Long id) {
        Car car = carRepository.findCarById(id);
        if (car == null) {
            throw new NotFoundException("Машина с id " + id + " не найдена");
        }
        return car;
    }

    public List<Car> filterCarsByAgeCountryAndColor(Integer age, String country, String color) {
        return carRepository.filterCarsByAgeCountryAndColor(age, country, color);
    }

    public List<Car> findCarsByPersonId(Long personId) {
        return carRepository.findCarByOwnerId(personId);
    }

    public Car findCarsByEvp(Long evp) {
        Car car = carRepository.findCarByEvp(evp);
        if(car == null){
            throw new NotFoundException("Машина с evp "+evp+" не найдена");
        }
        return car;
    }
}
