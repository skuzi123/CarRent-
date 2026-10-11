package com.carrent.boundedcontext.car.infrastructure.repository;

import com.carrent.boundedcontext.car.domain.model.aggregate.Car;
import com.carrent.boundedcontext.car.domain.model.valueobject.CarSpecification;
import com.carrent.boundedcontext.car.domain.model.valueobject.LicensePlate;
import com.carrent.boundedcontext.car.infrastructure.repository.persistence.CarJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CarPersistenceMapper {

    public Car toDomain(CarJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        var specification = new CarSpecification(
                entity.getMake(),
                entity.getModel(),
                entity.getYear(),
                entity.getColor());
        var licensePlate =
                new LicensePlate(entity.getLicensePlateNumber(), entity.getLicensePlateCountry());
        return Car.restore(
                entity.getId(),
                specification,
                licensePlate,
                entity.getMileage(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public CarJpaEntity toEntity(Car car) {
        if (car == null) {
            return null;
        }
        var entity = new CarJpaEntity();
        entity.setId(car.getId());
        entity.setMake(car.getSpecification().getMake());
        entity.setModel(car.getSpecification().getModel());
        entity.setYear(car.getSpecification().getYear());
        entity.setColor(car.getSpecification().getColor());
        entity.setLicensePlateNumber(car.getLicensePlate().getNumber());
        entity.setLicensePlateCountry(car.getLicensePlate().getCountry());
        entity.setMileage(car.getMileage());
        entity.setStatus(car.getStatus());
        entity.setCreatedAt(car.getCreatedAt());
        entity.setUpdatedAt(car.getUpdatedAt());
        return entity;
    }
}
