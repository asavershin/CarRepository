package asavershin.car.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "autoservice_ref")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Autoservice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autoservice_ref_id_seq")
    @SequenceGenerator(name = "autoservice_ref_id_seq", sequenceName = "autoservice_ref_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "autoservice", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars = new ArrayList<>();

}
