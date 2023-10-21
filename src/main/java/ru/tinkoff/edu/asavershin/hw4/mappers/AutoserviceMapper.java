package ru.tinkoff.edu.asavershin.hw4.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Autoservice;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseAutoservice;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestAutoservice;

@Mapper(componentModel = "spring", uses = Autoservice.class)
public interface AutoserviceMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "country", source = "country")
    ResponseAutoservice autoserviceToResponseAutoservice(Autoservice autoservice);
    Autoservice requestAutoserviceToAutoservice(RequestAutoservice request);
}