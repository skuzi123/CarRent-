package com.carrent.domain.model.aggregate;

import com.carrent.domain.model.valueobject.CarSpecification;
import com.carrent.domain.model.valueobject.CarStatus;
import com.carrent.domain.model.valueobject.LicensePlate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class Car {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CarSpecification specification;

    @Embedded
    private LicensePlate licensePlate;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    private Long mileage;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();

    public Car(CarSpecification specification, LicensePlate licensePlate, Long mileage) {
        this.specification = specification;
        this.licensePlate = licensePlate;
        this.mileage = mileage;
        this.status = CarStatus.AVAILABLE;
    }

    public void rent() {
        if (this.status != CarStatus.AVAILABLE) {
            throw new IllegalStateException("Car is not available for rent. Current status: " + this.status);
        }
        this.status = CarStatus.RENTED;
    }

    public void returnCar(Long newMileage) {
        if (this.status != CarStatus.RENTED) {
            throw new IllegalStateException("Car is not currently rented. Current status: " + this.status);
        }
        this.status = CarStatus.AVAILABLE;
        if (newMileage != null && newMileage > this.mileage) {
            this.mileage = newMileage;
        }
    }

    public void scheduleMaintenance() {
        if (this.status == CarStatus.RENTED) {
            throw new IllegalStateException("Cannot schedule maintenance for a rented car");
        }
        this.status = CarStatus.MAINTENANCE;
    }

    public void completeMaintenance() {
        if (this.status != CarStatus.MAINTENANCE) {
            throw new IllegalStateException("Car is not in maintenance");
        }
        this.status = CarStatus.AVAILABLE;
    }

    public void retire() {
        this.status = CarStatus.RETIRED;
    }

    public boolean isAvailable() {
        return this.status == CarStatus.AVAILABLE;
    }
}
