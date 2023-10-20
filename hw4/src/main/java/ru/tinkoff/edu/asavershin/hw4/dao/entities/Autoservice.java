package ru.tinkoff.edu.asavershin.hw4.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class Autoservice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_jn_seq")
    @SequenceGenerator(name = "car_jn_seq", sequenceName = "car_jn_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "adress")
    private String address;
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars;

}
