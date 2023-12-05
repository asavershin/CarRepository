package asavershin.car;

import asavershin.car.dao.entities.Autoservice;
import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Model;
import asavershin.car.dao.entities.Person;
import asavershin.car.dao.repositories.AutoserviceRepository;
import asavershin.car.dao.repositories.CarRepository;
import asavershin.car.dao.repositories.PersonRepository;
import asavershin.car.dto.autoservice.PersonFilter;
import asavershin.car.handlers.localexceptions.DuplicateEvpException;
import asavershin.car.handlers.localexceptions.NotFoundException;
import asavershin.car.services.PersonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(initializers = PostgreTestContainerConfig.Initializer.class)
public class PersonServiceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AutoserviceRepository autoserviceRepository;

    @BeforeEach
    @AfterEach
    public void clear() {
        carRepository.deleteAllInBatch();
        autoserviceRepository.deleteAll();
        personRepository.deleteAllInBatch();
    }


    @Test
    public void findByIdTest(){
        //given
        var person = new Person();
        person.setName("name");
        person.setAge(21);
        person = personRepository.save(person);
        //when
        person = personService.findPersonById(person.getId());
        var exception = assertThrows(NotFoundException.class,
                () -> personService.findPersonById(-1L));
        //then
        assertNotNull(person);
        assertEquals(person.getName(), "name");
        assertEquals(person.getAge(), 21);
        assertEquals(exception.getMessage(), "Человека с id -1 не найдено");

    }

    @Test
    public void createPerson() {
        // given
        Person testPerson = new Person();
        testPerson.setName("Имя Тестового Человека");
        testPerson.setAge(25);

        // when
        Person createdPerson = personService.createPerson(testPerson);

        //then
        var people = personRepository.findAll();
        assertEquals(1, people.size());

        createdPerson = people.get(0);
        assertNotNull(createdPerson.getId());

        Person retrievedPerson = personRepository.findById(createdPerson.getId()).orElse(null);
        assertNotNull(retrievedPerson);

        assertEquals(testPerson.getName(), retrievedPerson.getName());
        assertEquals(testPerson.getAge(), retrievedPerson.getAge());
    }

    @Test
    public void updatePerson() {
        // Given
        Person testPerson = new Person();
        testPerson.setName("Имя");
        testPerson.setAge(25);
        Person createdPerson = personRepository.save(testPerson);

        // When
        Person updatedPerson = personService.updatePerson(createdPerson.getId(), 30, "Новое Имя");

        // Then
        assertEquals(30, updatedPerson.getAge());
        assertEquals("Новое Имя", updatedPerson.getName());
    }

    @Test
    public void updateNotFoundException(){
        // When
        var exception = assertThrows(NotFoundException.class,
                () -> personService.updatePerson(1L, 30, "Новое Имя"));
        //then
        assertEquals(exception.getMessage(), "Человека с id 1 не найдено");
    }

    @Test
    @Transactional
    public void deleteById() {
        // Given
        var testPerson = Person.builder().name("").age(25).cars(new ArrayList<>()).build();

        var car1 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(1L).autoservice(null)
                .owner(testPerson).build();
        testPerson.getCars().add(car1);

        var createdPerson = personRepository.save(testPerson);
        car1 = createdPerson.getCars().get(0);

        // When
        personService.deleteById(createdPerson.getId());
        var exception = assertThrows(NotFoundException.class,
                () -> personService.deleteById(-1L));

        // Then
        assertFalse(personRepository.existsById(createdPerson.getId()));
        assertEquals(exception.getMessage(), "Человека с id -1 не найдено");
        assertNull(carRepository.findById(car1.getId()).orElseThrow().getOwner());
    }

    @Test
    @Transactional
    public void testCreatePersonWithCars() {
        // Given
        var testPerson = Person.builder().name("").age(25).cars(new ArrayList<>()).build();

        var car1 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(1L).autoservice(null)
                .owner(null).build();

        var car2 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(2L).autoservice(null)
                .owner(null).build();

        testPerson.setCars(List.of(car1, car2));

        // When
        personService.createPersonWithCars(testPerson);

        // Then
        var people = personRepository.findAll();
        assertEquals(1, people.size());
        var createdPerson = people.get(0);

        assertNotNull(createdPerson.getId());
        assertEquals(testPerson.getName(), createdPerson.getName());
        assertEquals(testPerson.getAge(), createdPerson.getAge());

        List<Car> cars = createdPerson.getCars();
        assertNotNull(cars);
        assertEquals(2, cars.size());

        cars.forEach(car -> assertEquals(createdPerson, car.getOwner()));

        cars.forEach(car -> assertNotNull(carRepository.findById(car.getId()).orElse(null)));
    }

    @Test
    public void createPWCException(){
        //given
        var car1 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(1L).autoservice(null)
                .owner(null).build();
        carRepository.save(car1);

        var testPerson = Person.builder().name("").age(25).cars(new ArrayList<>()).build();

        var car2 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(1L).autoservice(null)
                .owner(null).build();
        testPerson.getCars().add(car2);

        // When
        var exception = assertThrows(DuplicateEvpException.class,
                () -> personService.createPersonWithCars(testPerson));
        //then
        assertEquals(exception.getMessage(), "Одна или более из машин с evp 1 уже есть");

    }

    @Test
    public void findPeopleWithCarsFromCountryAndAmountTest() {
        // given
        var autoservice1 = Autoservice.builder().name("test").address("test").country("test").cars(new ArrayList<>()).build();
        var autoservice2 = Autoservice.builder().name("test").address("test").country("test2").cars(new ArrayList<>()).build();

        var person1 = Person.builder().name("name").age(32).cars(new ArrayList<>()).build();

        var person2 = Person.builder().name("name2").age(33).cars(new ArrayList<>()).build();


        var car1 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(2L)
                .owner(person1).autoservice(autoservice1).build();
        autoservice1.getCars().add(car1);

        var car2 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(3L)
                .owner(person1).autoservice(autoservice1).build();
        autoservice1.getCars().add(car2);

        person1.getCars().addAll(List.of(car1, car2));

        var car3 = Car.builder().releaseDate(LocalDateTime.now()).color("").model(Model.LADA).evp(4L)
                .owner(person1).autoservice(autoservice1).build();
        autoservice2.getCars().add(car3);
        person2.getCars().add(car3);

        PersonFilter filter = new PersonFilter();
        filter.setCountry(autoservice1.getCountry());
        filter.setAmount(2);

        personRepository.saveAllAndFlush(List.of(person1,person2));

        // when
        List<Person> result = personService.findPeopleWithCarsFromCountryAndAmount(0, 10, filter);

        // then
        assertEquals(1, result.size());
    }
}
