package asavershin.car.mappers;

import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Person;
import asavershin.car.dto.PageResponse;
import asavershin.car.dto.car.RequestCar;
import asavershin.car.dto.car.ResponseCar;
import asavershin.car.dto.person.RequestPersonWithCars;
import asavershin.car.utility.LocalDateTimeConverting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, AutoserviceMapper.class})
public interface CarMapper {

    @Mapping(target = "releaseDate", source = "releaseDate", qualifiedByName = "dateToString")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "autoservice", source = "autoservice")
    ResponseCar carToResponseCar(Car car);

    PageResponse<ResponseCar> pageResponseWithResponseCars(Page<Car> page);


    @Mapping(target = "releaseDate", source = "releaseDate", qualifiedByName = "stringToDate")
    Car requestCarToCar(RequestCar requestCar);

    Person personWithCars(RequestPersonWithCars request);

    @Named("dateToString")
    default String dateToString(LocalDateTime date){
        return LocalDateTimeConverting.localDateTimeToString(date);
    }

    @Named("stringToDate")
    default LocalDateTime stringToDate(String date) throws ParseException {
        return LocalDateTimeConverting.stringToLocalDateTime(date);
    }

}
