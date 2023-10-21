package ru.tinkoff.edu.asavershin.hw4.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestPerson;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponsePerson;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponsePersonWithCars;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarMapperWithoutOwner.class})
public interface PersonMapper {

    @Mapping(target = "personName", source = "name")
    @Mapping(target = "personAge", source = "age")
    @Mapping(target = "id", source = "id")
    ResponsePerson personToResponsePerson(Person person);

    @Mapping(target = "personName", source = "name")
    @Mapping(target = "personAge", source = "age")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "carsAmount", source = "cars", qualifiedByName = "carsAmount")
    @Mapping(target = "cars", source = "cars")
    ResponsePersonWithCars personToResponsePersonWithCars(Person person);

    Person requestPersonToPerson(RequestPerson request);
    @Named("carsAmount")
    default Integer carsAmount(List<Car> cars){
        return cars.size();
    }
}
