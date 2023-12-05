package asavershin.car.mappers;

import asavershin.car.dao.entities.Car;
import asavershin.car.dto.autoservice.ResponseAutoservice;
import asavershin.car.dto.car.ResponseCarWithoutOwner;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-05T22:00:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CarMapperWithoutOwnerImpl implements CarMapperWithoutOwner {

    @Autowired
    private AutoserviceMapper autoserviceMapper;

    @Override
    public ResponseCarWithoutOwner carToResponseCarWithoutOwner(Car car) {
        if ( car == null ) {
            return null;
        }

        String releaseDate = null;
        ResponseAutoservice autoservice = null;
        Long id = null;
        String color = null;
        String model = null;
        Long evp = null;

        releaseDate = dateToString( car.getReleaseDate() );
        autoservice = autoserviceMapper.autoserviceToResponseAutoservice( car.getAutoservice() );
        id = car.getId();
        color = car.getColor();
        if ( car.getModel() != null ) {
            model = car.getModel().name();
        }
        evp = car.getEvp();

        ResponseCarWithoutOwner responseCarWithoutOwner = new ResponseCarWithoutOwner( id, releaseDate, color, model, evp, autoservice );

        return responseCarWithoutOwner;
    }
}
