package com.carrent.boundedcontext.car.infrastructure.repository.persistence;

import com.carrent.boundedcontext.car.domain.model.valueobject.CarStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "cars", schema = "car")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class CarJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "production_year", nullable = false)
    private Integer year;

    private String color;

    @Column(name = "license_plate_number", nullable = false, unique = true)
    private String licensePlateNumber;

    @Column(name = "license_plate_country", nullable = false)
    private String licensePlateCountry;

    @Column(nullable = false)
    private Long mileage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarStatus status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
