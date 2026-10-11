package com.carrent.boundedcontext.rental.infrastructure.repository;

import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalStatus;
import com.carrent.boundedcontext.rental.infrastructure.repository.persistence.RentalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalSpringDataRepository extends JpaRepository<RentalJpaEntity, Long> {

    List<RentalJpaEntity> findByCustomerId(Long customerId);

    @Query("SELECT r FROM RentalJpaEntity r WHERE r.carId = :carId AND r.status IN :statuses")
    List<RentalJpaEntity> findActiveRentalsByCarId(
            @Param("carId") Long carId,
            @Param("statuses") List<RentalStatus> statuses);
}
