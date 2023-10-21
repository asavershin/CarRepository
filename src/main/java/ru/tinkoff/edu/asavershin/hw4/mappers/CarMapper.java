package ru.tinkoff.edu.asavershin.hw4.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestCar;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseCar;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseCarWithoutOwner;
import ru.tinkoff.edu.asavershin.hw4.utility.LocalDateTimeConverting;

import java.text.ParseException;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, AutoserviceMapper.class})
public interface CarMapper {

    @Mapping(target = "releaseDate", source = "releaseDate", qualifiedByName = "dateToString")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "autoservice", source = "autoservice")
    ResponseCar carToResponseCar(Car car);


    @Mapping(target = "releaseDate", source = "releaseDate", qualifiedByName = "stringToDate")
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