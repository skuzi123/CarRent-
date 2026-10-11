package com.carrent.boundedcontext.car.domain.model.aggregate;

import com.carrent.boundedcontext.car.domain.model.valueobject.CarSpecification;
import com.carrent.boundedcontext.car.domain.model.valueobject.CarStatus;
import com.carrent.boundedcontext.car.domain.model.valueobject.LicensePlate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Car {

    private Long id;
    private CarSpecification specification;
    private LicensePlate licensePlate;
    private Long mileage;
    private CarStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Car(CarSpecification specification, LicensePlate licensePlate, Long mileage) {
        this(null, specification, licensePlate, mileage, CarStatus.AVAILABLE, null, null);
    }

    public static Car restore(
            Long id,
            CarSpecification specification,
            LicensePlate licensePlate,
            Long mileage,
            CarStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        return new Car(id, specification, licensePlate, mileage, status, createdAt, updatedAt);
    }

    private Car(
            Long id,
            CarSpecification specification,
            LicensePlate licensePlate,
            Long mileage,
            CarStatus status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.specification = specification;
        this.licensePlate = licensePlate;
        this.mileage = mileage;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
