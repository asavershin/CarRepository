package asavershin.car;

import asavershin.car.dao.entities.Autoservice;
import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Model;
import asavershin.car.dao.entities.Person;
import asavershin.car.dao.repositories.AutoserviceRepository;
import asavershin.car.dao.repositories.CarRepository;
import asavershin.car.dao.repositories.PersonRepository;
import asavershin.car.dto.car.CarFilter;
import asavershin.car.handlers.localexceptions.DuplicateEvpException;
import asavershin.car.handlers.localexceptions.NotFoundException;
import asavershin.car.services.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.*;


@SpringBootTest
@ContextConfiguration(initializers = PostgreTestContainerConfig.Initializer.class)
public class CarServiceTest {
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AutoserviceRepository autoserviceRepository;

    @BeforeEach
    @AfterEach
    public void clear() {
        carRepository.deleteAll();
        personRepository.deleteAll();
        autoserviceRepository.deleteAll();
    }

    @Test
    @Transactional
    void updateCar() {
        // given
        var person = Person.builder().name("").age(30).cars(new ArrayList<>()).build();

        var autoservice = Autoservice.builder().name("").address("").country("").cars(new ArrayList<>()).build();

        var car = Car.builder().evp(1L).releaseDate(LocalDateTime.now()).model(Model.LADA)
                .color("old").owner(person).autoservice(autoservice).build();
        person.getCars().add(car);
        autoservice.getCars().add(car);
        car = carRepository.save(car);

        // when
        var updatedCar = carService.updateCar(car.getId(), "new", null, null);
        autoservice = autoserviceRepository.findAll().get(0);
        person = personRepository.findAll().get(0);

        // then
        assertEquals("new", updatedCar.getColor());
        assertEquals(car.getId(), updatedCar.getId());
        assertEquals(person.getCars().size(), 0);
        assertNull(updatedCar.getOwner());
        assertEquals(autoservice.getCars().size(), 0);
        assertNull(updatedCar.getAutoservice());
    }

    private static Stream<Arguments> ExceptionsData() {
        return Stream.of(
                Arguments.of(Car.builder().color("").model(Model.LADA).releaseDate(LocalDateTime.now()).evp(1L).build(), "new", null, -1L, "Человека с Id " + -1L + " не найдено"),
                Arguments.of(Car.builder().color("").model(Model.LADA).releaseDate(LocalDateTime.now()).evp(1L).build(), "new", -1L, null, "Автосервиса с Id " + -1L + " не найдено"),
                Arguments.of(null, "new", -1L, null, "Машины с Id " + -1L + " не найдено"));
    }

    @ParameterizedTest
    @MethodSource("ExceptionsData")
    public void updateCarExceptions(Car car, String name, Long autoserviceId, Long personId, String exception){
        // given
        Car createCar;
        if(car != null){
            createCar = carRepository.save(car);
        } else {
            createCar = null;
        }
        // when
        var exceptionTest = assertThrows(NotFoundException.class,
                () -> carService.updateCar(createCar == null? -1L:createCar.getId(),name, personId, autoserviceId));

        // then
        assertEquals(exceptionTest.getMessage(), exception);
    }

    @Test
    void createCar() {
        // given
        var car = Car.builder().color("").releaseDate(LocalDateTime.now()).model(Model.LADA).evp(1L).build();
        var car1 = Car.builder().color("").releaseDate(LocalDateTime.now()).model(Model.LADA).evp(1L).build();
        carRepository.save(car1);

        // when
        var exception = assertThrows(DuplicateEvpException.class,
                () -> carService.createCar(car));

        car.setEvp(2L);
        var createdCar = carService.createCar(car);


        // then
        assertNotNull(createdCar.getId());
        assertEquals(car.getColor(), createdCar.getColor());
        assertEquals(car.getEvp(), createdCar.getEvp());
        assertEquals("Такой EVP 1 уже есть", exception.getMessage());
    }

    @Test
    void findCarByEvpTest() {
        // given
        var car1 = Car.builder().color("").evp(1L).releaseDate(LocalDateTime.now()).model(Model.LADA).build();
        Car createdCar = carRepository.save(car1);

        // when
        Car foundCar = carService.findCarByEvp(createdCar.getEvp());

        // then
        assertNotNull(foundCar.getId());
        assertEquals(createdCar.getId(), foundCar.getId());
        assertEquals(createdCar.getColor(), foundCar.getColor());
        assertEquals(createdCar.getEvp(), foundCar.getEvp());
        assertThrows(NotFoundException.class, () -> carService.findCarByEvp(-1L));
    }

    @Test
    @Transactional
    void deleteCar() {
        // given
        var car = Car.builder().color("").evp(1L).releaseDate(LocalDateTime.now()).model(Model.LADA).build();

        var autoservice = Autoservice.builder().name("").address("").country("").cars(new ArrayList<>()).build();
        autoservice.getCars().add(car);

        var person = Person.builder().name("").age(23).cars(new ArrayList<>()).build();
        person.getCars().add(car);

        car.setOwner(person);
        car.setAutoservice(autoservice);

        car = carRepository.save(car);
        var personId = car.getOwner().getId();
        var autoserviceId = car.getAutoservice().getId();

        // when
        carService.deleteCar(car.getId());

        // then
        assertFalse(carRepository.existsById(car.getId()));
        assertEquals(personRepository.findPersonById(personId).getCars().size(), 0);
        assertEquals(autoserviceRepository.findAutoserviceById(autoserviceId).getCars().size(), 0);
        assertThrows(NotFoundException.class, () -> carService.deleteCar(-1L));
    }

    @Test
    void testCarFilter(){
        // Given
        var car1 = Car.builder().color("").evp(1L).releaseDate(LocalDateTime.now()).model(Model.LADA).color("Red").build();
        var car2 = Car.builder().color("").evp(2L).releaseDate(LocalDateTime.now()).model(Model.LADA).color("Black").build();

        var person1 = Person.builder().age(21).name("person1").cars(new ArrayList<>()).build();
        person1.getCars().add(car1);
        car1.setOwner(person1);
        var person2 = Person.builder().age(18).name("person2").cars(new ArrayList<>()).build();
        person2.getCars().add(car2);
        car2.setOwner(person2);

        var autoservice1 = Autoservice.builder().address("").cars(new ArrayList<>()).country("Russia").name("").build();
        autoservice1.getCars().add(car1);
        car1.setAutoservice(autoservice1);
        var autoservice2 = Autoservice.builder().address("").cars(new ArrayList<>()).country("Russia").name("").build();
        autoservice2.getCars().add(car2);
        car2.setAutoservice(autoservice2);

        car1 = carRepository.save(car1);
        carRepository.save(car2);

        var carFilter = CarFilter.builder().color("Red").age(20).country("Russia").build();

        // When
        var cars = carService.filterCarsByAgeCountryAndColor(0, 10, carFilter.toPredicate()).toList();

        // Then
        assertEquals(1, cars.size());
        assertEquals(car1.getColor(), cars.get(0).getColor());
        assertEquals(car1.getId(), cars.get(0).getId());
        assertNotNull(cars.get(0).getOwner());
        assertNotNull(cars.get(0).getAutoservice());
    }

    @Test
    void findCarsByPersonIdTest(){
        // Given
        var person1 = Person.builder().age(21).name("person1").cars(new ArrayList<>()).build();
        var car1 = Car.builder().color("").evp(1L).releaseDate(LocalDateTime.now()).model(Model.LADA)
                .color("Red").owner(person1).build();
        person1.getCars().add(car1);
        car1 = carRepository.save(car1);

        // When
        List<Car> cars = carService.findCarsByPersonId(car1.getOwner().getId());
        var ex = assertThrows(NotFoundException.class, () -> carService.findCarsByPersonId(-1L));
        // Then

        assertEquals(1, cars.size());
        assertEquals(car1.getId(), cars.get(0).getId());
        assertNotNull(cars.get(0).getOwner());
        assertEquals("Человека с id -1 не существует", ex.getMessage());
    }

    @Test
    void findCarByIdTest(){
        // Given
        var person1 = Person.builder().age(21).name("person1").cars(new ArrayList<>()).build();
        var car1 = Car.builder().color("").evp(1L).releaseDate(LocalDateTime.now()).model(Model.LADA)
                .color("Red").owner(person1).build();
        person1.getCars().add(car1);
        car1 = carRepository.save(car1);

        // When
        var car = carService.getCarById(car1.getId());
        var ex = assertThrows(NotFoundException.class, () -> carService.getCarById(-1L));

        // Then
        assertEquals(car1.getColor(), car1.getColor());
        assertEquals(car1.getId(), car.getId());
        assertNotNull(car.getOwner());
        assertEquals("Машина с id -1 не найдена", ex.getMessage());
    }
}
