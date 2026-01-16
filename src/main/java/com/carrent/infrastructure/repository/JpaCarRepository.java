package com.carrent.infrastructure.repository;

import com.carrent.domain.model.aggregate.Car;
import com.carrent.domain.model.valueobject.CarStatus;
import com.carrent.domain.repository.CarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(CarStatus status);
}
