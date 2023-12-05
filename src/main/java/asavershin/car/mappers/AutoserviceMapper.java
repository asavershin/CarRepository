package asavershin.car.mappers;

import asavershin.car.dao.entities.Autoservice;
import asavershin.car.dto.autoservice.RequestAutoservice;
import asavershin.car.dto.autoservice.ResponseAutoservice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = Autoservice.class)
public interface AutoserviceMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "country", source = "country")
    ResponseAutoservice autoserviceToResponseAutoservice(Autoservice autoservice);
    Autoservice requestAutoserviceToAutoservice(RequestAutoservice request);
}
