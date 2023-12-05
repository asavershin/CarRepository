package asavershin.car.mappers;

import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Person;
import asavershin.car.dto.PageResponse;
import asavershin.car.dto.person.RequestPerson;
import asavershin.car.dto.person.ResponsePerson;
import asavershin.car.dto.person.ResponsePersonWithCars;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarMapperWithoutOwner.class})
public interface PersonMapper {

    @Mapping(target = "personName", source = "name")
    @Mapping(target = "personAge", source = "age")
    @Mapping(target = "id", source = "id")
    ResponsePerson personToResponsePerson(Person person);

    PageResponse<ResponsePersonWithCars> pageResponseWithResponsePersonWithCars(Page<Person> page);
    List<ResponsePersonWithCars> pageResponseWithResponsePersonWithCars(List<Person> persons);

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
