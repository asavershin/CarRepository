package asavershin.car.dao.repositories;

import asavershin.car.dao.entities.Car;
import asavershin.car.dao.entities.QAutoservice;
import asavershin.car.dao.entities.QCar;
import asavershin.car.dao.entities.QPerson;
import asavershin.car.dto.autoservice.PersonFilter;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomPersonRepository{
    private final EntityManager entityManager;

    public Slice<Car> findCarOfPeopleWithCarsFromCountry(int pageNumber, int pageSize, PersonFilter filter) {
        QCar car = QCar.car;
        QAutoservice autoservice = QAutoservice.autoservice;
        QPerson owner = QPerson.person;

        JPAQuery<Car> query = new JPAQuery<>(entityManager);

        List<Car> cars = query.select(car)
                .from(car)
                .leftJoin(car.owner, owner).fetchJoin()
                .leftJoin(car.autoservice, autoservice).fetchJoin()
                .where(
                        filter.getCountry() == null ? null : autoservice.country.equalsIgnoreCase(filter.getCountry()),
                        filter.getAmount() == null ? null : owner.id.in(
                                JPAExpressions.select(car.owner.id)
                                        .from(car)
                                        .join(car.autoservice, autoservice)
                                        .where(autoservice.country.equalsIgnoreCase(filter.getCountry()))
                                        .groupBy(car.owner.id)
                                        .having(car.count().gt(filter.getAmount()))
                        )
                )
                .offset(pageNumber * pageSize)
                .limit(pageSize)
                .fetch();

        return new SliceImpl<>(cars, PageRequest.of(pageNumber, pageSize), true);
    }

}
