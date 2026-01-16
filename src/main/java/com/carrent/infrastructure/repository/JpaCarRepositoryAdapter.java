package com.carrent.infrastructure.repository;

import com.carrent.domain.model.aggregate.Car;
import com.carrent.domain.model.valueobject.CarStatus;
import com.carrent.domain.repository.CarRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCarRepositoryAdapter implements CarRepository {

    private final JpaCarRepository jpaCarRepository;

    public JpaCarRepositoryAdapter(JpaCarRepository jpaCarRepository) {
        this.jpaCarRepository = jpaCarRepository;
    }

    @Override
    public Car save(Car car) {
        return jpaCarRepository.save(car);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return jpaCarRepository.findById(id);
    }

    @Override
    public List<Car> findAll() {
        return jpaCarRepository.findAll();
    }

    @Override
    public List<Car> findByStatus(CarStatus status) {
        return jpaCarRepository.findByStatus(status);
    }

    @Override
    public void deleteById(Long id) {
        jpaCarRepository.deleteById(id);
    }
}
