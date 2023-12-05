package asavershin.car.mappers;

import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Model;
import asavershin.car.dao.entities.Person;
import asavershin.car.dto.PageResponse;
import asavershin.car.dto.autoservice.ResponseAutoservice;
import asavershin.car.dto.car.RequestCar;
import asavershin.car.dto.car.ResponseCar;
import asavershin.car.dto.person.RequestPersonWithCars;
import asavershin.car.dto.person.ResponsePerson;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-05T22:00:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CarMapperImpl implements CarMapper {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private AutoserviceMapper autoserviceMapper;

    @Override
    public ResponseCar carToResponseCar(Car car) {
        if ( car == null ) {
            return null;
        }

        String releaseDate = null;
        ResponsePerson owner = null;
        ResponseAutoservice autoservice = null;
        Long id = null;
        String color = null;
        String model = null;
        Long evp = null;
        String createdAt = null;
        String lastUpdatedAt = null;

        releaseDate = dateToString( car.getReleaseDate() );
        owner = personMapper.personToResponsePerson( car.getOwner() );
        autoservice = autoserviceMapper.autoserviceToResponseAutoservice( car.getAutoservice() );
        id = car.getId();
        color = car.getColor();
        if ( car.getModel() != null ) {
            model = car.getModel().name();
        }
        evp = car.getEvp();
        if ( car.getCreatedAt() != null ) {
            createdAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( car.getCreatedAt() );
        }
        if ( car.getLastUpdatedAt() != null ) {
            lastUpdatedAt = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( car.getLastUpdatedAt() );
        }

        ResponseCar responseCar = new ResponseCar( id, releaseDate, color, model, evp, createdAt, lastUpdatedAt, owner, autoservice );

        return responseCar;
    }

    @Override
    public PageResponse<ResponseCar> pageResponseWithResponseCars(Page<Car> page) {
        if ( page == null ) {
            return null;
        }

        PageResponse<ResponseCar> pageResponse = new PageResponse<ResponseCar>();

        pageResponse.setTotalPages( page.getTotalPages() );
        pageResponse.setTotalElements( page.getTotalElements() );
        pageResponse.setNumber( page.getNumber() );
        pageResponse.setSize( page.getSize() );
        if ( page.hasContent() ) {
            pageResponse.setContent( carListToResponseCarList( page.getContent() ) );
        }

        return pageResponse;
    }

    @Override
    public Car requestCarToCar(RequestCar requestCar) {
        if ( requestCar == null ) {
            return null;
        }

        Car.CarBuilder car = Car.builder();

        try {
            car.releaseDate( stringToDate( requestCar.getReleaseDate() ) );
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        car.color( requestCar.getColor() );
        if ( requestCar.getModel() != null ) {
            car.model( Enum.valueOf( Model.class, requestCar.getModel() ) );
        }
        car.evp( requestCar.getEvp() );

        return car.build();
    }

    @Override
    public Person personWithCars(RequestPersonWithCars request) {
        if ( request == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.name( request.getName() );
        person.age( request.getAge() );
        person.cars( requestCarListToCarList( request.getCars() ) );

        return person.build();
    }

    protected List<ResponseCar> carListToResponseCarList(List<Car> list) {
        if ( list == null ) {
            return null;
        }

        List<ResponseCar> list1 = new ArrayList<ResponseCar>( list.size() );
        for ( Car car : list ) {
            list1.add( carToResponseCar( car ) );
        }

        return list1;
    }

    protected List<Car> requestCarListToCarList(List<RequestCar> list) {
        if ( list == null ) {
            return null;
        }

        List<Car> list1 = new ArrayList<Car>( list.size() );
        for ( RequestCar requestCar : list ) {
            list1.add( requestCarToCar( requestCar ) );
        }

        return list1;
    }
}
