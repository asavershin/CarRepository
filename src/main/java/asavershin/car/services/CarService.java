package asavershin.car.services;

import asavershin.car.dao.entities.Autoservice;
import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Person;
import asavershin.car.dao.repositories.AutoserviceRepository;
import asavershin.car.dao.repositories.CarRepository;
import asavershin.car.dao.repositories.PersonRepository;
import asavershin.car.handlers.localexceptions.DuplicateEvpException;
import asavershin.car.handlers.localexceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;
    private final AutoserviceRepository autoserviceRepository;

    @Transactional
    public Car createCar(Car car){
        if (carRepository.existsByEvp(car.getEvp())) {
            throw new DuplicateEvpException("Такой EVP "+ car.getEvp() + " уже есть");
        }
        return carRepository.save(car);
    }

    @Transactional
    public Car updateCar(long id, String color, Long personId, Long autoserviceId){
        Car car = carRepository.findCarById(id);
        if(car==null){
            throw new NotFoundException("Машины с Id " + id + " не найдено");
        }

        if(car.getOwner() != null){
            car.getOwner().getCars().remove(car);

        }
        if(car.getAutoservice() != null){
            car.getAutoservice().getCars().remove(car);

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

    @Transactional
    public void deleteCar(Long id) {
        var car = carRepository.findCarById(id);
        if (car == null){
            throw new NotFoundException("Машина с id " + id + " не найдена");
        }
        var autoservice = car.getAutoservice();
        if(autoservice != null) {
            autoservice.getCars().remove(car);
            car.setAutoservice(null);
        }
        var person = car.getOwner();
        if(person != null){
            person.getCars().remove(car);
            car.setOwner(null);
        }
        carRepository.delete(car);
    }

    public Car getCarById(Long id) {
        Car car = carRepository.findCarById(id);
        if (car == null) {
            throw new NotFoundException("Машина с id " + id + " не найдена");
        }
        return car;
    }

    public Page<Car> filterCarsByAgeCountryAndColor(int pageNumber, int pageSize, Predicate predicate) {
        return carRepository.findAll(predicate, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc("createdAt"))));
    }

    public List<Car> findCarsByPersonId(Long personId) {
        if(!personRepository.existsById(personId)){
            throw new NotFoundException("Человека с id " + personId + " не существует");
        }
        return carRepository.findCarByOwnerId(personId);
    }

    public Car findCarByEvp(Long evp) {
        Car car = carRepository.findCarByEvp(evp);
        if(car == null){
            throw new NotFoundException("Машина с evp "+evp+" не найдена");
        }
        return car;
    }
}
