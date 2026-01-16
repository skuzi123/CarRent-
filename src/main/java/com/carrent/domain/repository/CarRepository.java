package com.carrent.domain.repository;

import com.carrent.domain.model.aggregate.Car;
import com.carrent.domain.model.valueobject.CarStatus;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);
    Optional<Car> findById(Long id);
    List<Car> findAll();
    List<Car> findByStatus(CarStatus status);
    void deleteById(Long id);
}
