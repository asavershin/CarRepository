package ru.tinkoff.edu.asavershin.hw4.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Car;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseCarWithoutOwner;
import ru.tinkoff.edu.asavershin.hw4.utility.LocalDateTimeConverting;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = {AutoserviceMapper.class})
public interface CarMapperWithoutOwner {
    @Mapping(target = "releaseDate", source = "releaseDate", qualifiedByName = "dateToString")
    @Mapping(target = "autoservice", source = "autoservice")
    ResponseCarWithoutOwner carToResponseCarWithoutOwner(Car car);

    @Named("dateToString")
    default String dateToString(LocalDateTime date){
        return LocalDateTimeConverting.localDateTimeToString(date);
    }
}
