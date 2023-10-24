package ru.tinkoff.edu.asavershin.hw4.dao.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "car_jn")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_jn_seq")
    @SequenceGenerator(name = "car_jn_seq", sequenceName = "car_jn_seq", allocationSize = 1)
    private Long id;
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
    @Column(name = "color")
    private String color;
    @Enumerated(EnumType.STRING)
    @Column(name = "model")
    private Model model;
    @Column(name = "evp")
    private Long evp;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_updated_at")
    private LocalDateTime lastUpdatedAt;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    private Person owner;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "autoservice_id")
    private Autoservice autoservice;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastUpdatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }

}
