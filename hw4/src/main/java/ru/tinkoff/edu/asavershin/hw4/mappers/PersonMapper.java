package ru.tinkoff.edu.asavershin.hw4.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.edu.asavershin.hw4.dto.PersonRequest;
import ru.tinkoff.edu.asavershin.hw4.dto.PersonRequestForCreate;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponsePerson;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @Mapping(target = "personName", source = "name")
    @Mapping(target = "personAge", source = "age")
    ResponsePerson personToResponsePerson(Person person);

    Person requestPersonToPerson(PersonRequest request);
}
