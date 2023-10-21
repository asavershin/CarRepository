package ru.tinkoff.edu.asavershin.hw4.dao.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@Table(name = "autoservice_ref")
public class Autoservice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autoservice_ref_seq")
    @SequenceGenerator(name = "autoservice_ref_seq", sequenceName = "autoservice_ref_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "autoservice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Car> cars;

}
