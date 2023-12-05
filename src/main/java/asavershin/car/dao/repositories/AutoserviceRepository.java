package asavershin.car.dao.repositories;

import asavershin.car.dao.entities.Autoservice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoserviceRepository extends JpaRepository<Autoservice, Long> {
    Autoservice findAutoserviceById(Long autoserviceId);

}
