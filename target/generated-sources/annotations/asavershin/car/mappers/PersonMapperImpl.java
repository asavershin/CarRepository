package asavershin.car.mappers;

import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.Person;
import asavershin.car.dto.PageResponse;
import asavershin.car.dto.car.ResponseCarWithoutOwner;
import asavershin.car.dto.person.RequestPerson;
import asavershin.car.dto.person.ResponsePerson;
import asavershin.car.dto.person.ResponsePersonWithCars;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-05T22:00:51+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Autowired
    private CarMapperWithoutOwner carMapperWithoutOwner;

    @Override
    public ResponsePerson personToResponsePerson(Person person) {
        if ( person == null ) {
            return null;
        }

        String personName = null;
        Integer personAge = null;
        Long id = null;

        personName = person.getName();
        personAge = person.getAge();
        id = person.getId();

        ResponsePerson responsePerson = new ResponsePerson( id, personName, personAge );

        return responsePerson;
    }

    @Override
    public PageResponse<ResponsePersonWithCars> pageResponseWithResponsePersonWithCars(Page<Person> page) {
        if ( page == null ) {
            return null;
        }

        PageResponse<ResponsePersonWithCars> pageResponse = new PageResponse<ResponsePersonWithCars>();

        pageResponse.setTotalPages( page.getTotalPages() );
        pageResponse.setTotalElements( page.getTotalElements() );
        pageResponse.setNumber( page.getNumber() );
        pageResponse.setSize( page.getSize() );
        if ( page.hasContent() ) {
            pageResponse.setContent( pageResponseWithResponsePersonWithCars( page.getContent() ) );
        }

        return pageResponse;
    }

    @Override
    public List<ResponsePersonWithCars> pageResponseWithResponsePersonWithCars(List<Person> persons) {
        if ( persons == null ) {
            return null;
        }

        List<ResponsePersonWithCars> list = new ArrayList<ResponsePersonWithCars>( persons.size() );
        for ( Person person : persons ) {
            list.add( personToResponsePersonWithCars( person ) );
        }

        return list;
    }

    @Override
    public ResponsePersonWithCars personToResponsePersonWithCars(Person person) {
        if ( person == null ) {
            return null;
        }

        String personName = null;
        Integer personAge = null;
        Long id = null;
        Integer carsAmount = null;
        List<ResponseCarWithoutOwner> cars = null;

        personName = person.getName();
        personAge = person.getAge();
        id = person.getId();
        carsAmount = carsAmount( person.getCars() );
        cars = carListToResponseCarWithoutOwnerList( person.getCars() );

        ResponsePersonWithCars responsePersonWithCars = new ResponsePersonWithCars( id, personName, personAge, carsAmount, cars );

        return responsePersonWithCars;
    }

    @Override
    public Person requestPersonToPerson(RequestPerson request) {
        if ( request == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.name( request.getName() );
        person.age( request.getAge() );

        return person.build();
    }

    protected List<ResponseCarWithoutOwner> carListToResponseCarWithoutOwnerList(List<Car> list) {
        if ( list == null ) {
            return null;
        }

        List<ResponseCarWithoutOwner> list1 = new ArrayList<ResponseCarWithoutOwner>( list.size() );
        for ( Car car : list ) {
            list1.add( carMapperWithoutOwner.carToResponseCarWithoutOwner( car ) );
        }

        return list1;
    }
}
