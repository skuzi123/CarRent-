package com.carrent.boundedcontext.car.infrastructure.repository;

import com.carrent.boundedcontext.car.domain.model.valueobject.CarStatus;
import com.carrent.boundedcontext.car.infrastructure.repository.persistence.CarJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSpringDataRepository extends JpaRepository<CarJpaEntity, Long> {

    List<CarJpaEntity> findByStatus(CarStatus status);
}
