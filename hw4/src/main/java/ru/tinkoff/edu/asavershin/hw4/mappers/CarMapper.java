package ru.tinkoff.edu.asavershin.hw4.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestCar;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseCar;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.utility.LocalDateTimeConverting;

import java.text.ParseException;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface CarMapper {

    @Mapping(target = "created", source = "createdAt", qualifiedByName = "dateToString")
    @Mapping(target = "destroyed", source = "destroyedAt", qualifiedByName = "dateToString")
    @Mapping(target = "responsePerson", source = "owner")
    ResponseCar carToResponseCar(Car car);

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "stringToDate")
    @Mapping(target = "destroyedAt", source = "destroyedAt", qualifiedByName = "stringToDate")
    Car requestCarToCar(RequestCar requestCar);

    @Named("dateToString")
    default String dateToString(LocalDateTime date){
        return LocalDateTimeConverting.localDateTimeToString(date);
    }

    @Named("stringToDate")
    default LocalDateTime StringToDate(String date) throws ParseException {
        return LocalDateTimeConverting.stringToLocalDateTime(date);
    }

}
