package asavershin.car.dao.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAutoservice is a Querydsl query type for Autoservice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAutoservice extends EntityPathBase<Autoservice> {

    private static final long serialVersionUID = 755595247L;

    public static final QAutoservice autoservice = new QAutoservice("autoservice");

    public final StringPath address = createString("address");

    public final ListPath<Car, QCar> cars = this.<Car, QCar>createList("cars", Car.class, QCar.class, PathInits.DIRECT2);

    public final StringPath country = createString("country");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QAutoservice(String variable) {
        super(Autoservice.class, forVariable(variable));
    }

    public QAutoservice(Path<? extends Autoservice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAutoservice(PathMetadata metadata) {
        super(Autoservice.class, metadata);
    }

}

