package asavershin.car.mappers;

import asavershin.car.dao.entities.Car;
import asavershin.car.dto.car.ResponseCarWithoutOwner;
import asavershin.car.utility.LocalDateTimeConverting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
