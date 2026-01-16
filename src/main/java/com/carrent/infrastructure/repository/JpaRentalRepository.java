package com.carrent.infrastructure.repository;

import com.carrent.domain.model.aggregate.Rental;
import com.carrent.domain.model.valueobject.RentalStatus;
import com.carrent.domain.repository.RentalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomerId(Long customerId);
    
    @Query("SELECT r FROM Rental r WHERE r.car.id = :carId AND r.status IN :statuses")
    List<Rental> findActiveRentalsByCarId(@Param("carId") Long carId, 
                                          @Param("statuses") List<RentalStatus> statuses);
}
