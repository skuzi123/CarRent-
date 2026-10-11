package com.carrent.boundedcontext.car.infrastructure.repository;

import com.carrent.boundedcontext.car.domain.model.aggregate.Car;
import com.carrent.boundedcontext.car.domain.model.valueobject.CarStatus;
import com.carrent.boundedcontext.car.domain.repository.CarRepository;
import com.carrent.boundedcontext.car.infrastructure.repository.persistence.CarJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCarRepositoryAdapter implements CarRepository {

    private final CarSpringDataRepository springDataRepository;
    private final CarPersistenceMapper persistenceMapper;

    public JpaCarRepositoryAdapter(
            CarSpringDataRepository springDataRepository,
            CarPersistenceMapper persistenceMapper) {
        this.springDataRepository = springDataRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Car save(Car car) {
        CarJpaEntity entity = persistenceMapper.toEntity(car);
        CarJpaEntity saved = springDataRepository.save(entity);
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return springDataRepository.findById(id).map(persistenceMapper::toDomain);
    }

    @Override
    public List<Car> findAll() {
        return springDataRepository.findAll().stream().map(persistenceMapper::toDomain).toList();
    }

    @Override
    public List<Car> findByStatus(CarStatus status) {
        return springDataRepository.findByStatus(status).stream()
                .map(persistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
