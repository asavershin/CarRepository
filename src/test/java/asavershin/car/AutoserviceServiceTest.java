package asavershin.car;

import asavershin.car.TestContext;
import asavershin.car.dao.entities.Autoservice;
import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Model;
import asavershin.car.dao.entities.Person;
import asavershin.car.dao.repositories.AutoserviceRepository;
import asavershin.car.dao.repositories.CarRepository;
import asavershin.car.dao.repositories.PersonRepository;
import asavershin.car.handlers.localexceptions.NotFoundException;
import asavershin.car.services.AutoserviceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class AutoserviceServiceTest extends TestContext {
    @Autowired
    private AutoserviceService autoserviceService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AutoserviceRepository autoserviceRepository;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    public void clear() {
        carRepository.deleteAllInBatch();
        autoserviceRepository.deleteAll();
        personRepository.deleteAllInBatch();
    }

    @Test
    void createAutoserviceTest() {
        // given
        var autoservice = Autoservice.builder().name("name").address("address").country("country").cars(new ArrayList<>()).build();

        // when
        Autoservice createdAutoservice = autoserviceService.createAutoservice(autoservice);

        // then
        assertNotNull(createdAutoservice.getId());
        assertEquals(autoservice.getName(), createdAutoservice.getName());
        assertEquals(autoservice.getAddress(), createdAutoservice.getAddress());
        assertEquals(autoservice.getCountry(), createdAutoservice.getCountry());
    }

    @Test
    void updateAutoservice() {
        // given
        var autoservice = Autoservice.builder().name("name").address("address").country("country").cars(new ArrayList<>()).build();
        var createdAutoservice = autoserviceRepository.save(autoservice);

        // when
        var updatedAutoservice = autoserviceService.updateAutoservice(
                createdAutoservice.getId(),
                "new",
                "new",
                "new"
        );

        // then
        assertEquals(createdAutoservice.getId(), updatedAutoservice.getId());
        assertEquals("new", updatedAutoservice.getName());
        assertEquals("new", updatedAutoservice.getAddress());
        assertEquals("new", updatedAutoservice.getCountry());
    }

    @Test
    void updateAutoserviceException() {
        // then
        assertThrows(NotFoundException.class, () -> autoserviceService.updateAutoservice(
                123L, "", "", ""));
    }

    @Test
    @Transactional
    void deleteAutoservice() {
        // given
        var autoservice = Autoservice.builder().name("name").address("address").country("country").cars(new ArrayList<>()).build();

        var person = Person.builder().name("name").age(21).cars(new ArrayList<>()).build();

        var car1 = Car.builder().releaseDate(LocalDateTime.now()).color("blue").model(Model.LADA)
                .evp(2L).owner(person).autoservice(autoservice).build();

        var car2 = Car.builder().releaseDate(LocalDateTime.now()).color("red").model(Model.MOSKVITCH)
                .evp(1L).owner(person).autoservice(autoservice).build();

        autoservice.getCars().addAll(List.of(car1, car2));

        personRepository.save(person);

        Autoservice createdAutoservice = autoserviceRepository.save(autoservice);
        carRepository.saveAll(List.of(car1, car2));

        // when
        autoserviceService.deleteAutoservice(createdAutoservice.getId());

        // then
        assertFalse(autoserviceRepository.existsById(createdAutoservice.getId()));
        assertNull(carRepository.findById(car1.getId()).orElse(null).getAutoservice());
        assertNull(carRepository.findById(car2.getId()).orElse(null).getAutoservice());
    }

    @Test
    void deleteAutoserviceException() {
        // then
        assertThrows(NotFoundException.class, () -> autoserviceService.deleteAutoservice(123L));
    }

    @Test
    void getAutoservice() {
        // given
        var autoservice = Autoservice.builder().name("test").address("address").country("country").cars(new ArrayList<>()).build();
        var createdAutoservice = autoserviceRepository.save(autoservice);

        // when
        var retrievedAutoservice = autoserviceService.getAutoservice(createdAutoservice.getId());

        // then
        assertNotNull(retrievedAutoservice);
        assertEquals(createdAutoservice.getId(), retrievedAutoservice.getId());
        assertEquals(createdAutoservice.getName(), retrievedAutoservice.getName());
        assertEquals(createdAutoservice.getAddress(), retrievedAutoservice.getAddress());
        assertEquals(createdAutoservice.getCountry(), retrievedAutoservice.getCountry());
    }

    @Test
    void getAutoserviceException() {
        //given
        var id = -1L;
        // then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> autoserviceService.getAutoservice(id));

        assertEquals("Салон с Id " + id + " не найден", exception.getMessage());
    }
}
