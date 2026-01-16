package com.carrent.infrastructure.repository;

import com.carrent.domain.model.aggregate.Rental;
import com.carrent.domain.model.valueobject.RentalStatus;
import com.carrent.domain.repository.RentalRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JpaRentalRepositoryAdapter implements RentalRepository {

    private final JpaRentalRepository jpaRentalRepository;

    public JpaRentalRepositoryAdapter(JpaRentalRepository jpaRentalRepository) {
        this.jpaRentalRepository = jpaRentalRepository;
    }

    @Override
    public Rental save(Rental rental) {
        return jpaRentalRepository.save(rental);
    }

    @Override
    public Optional<Rental> findById(Long id) {
        return jpaRentalRepository.findById(id);
    }

    @Override
    public List<Rental> findAll() {
        return jpaRentalRepository.findAll();
    }

    @Override
    public List<Rental> findByCustomerId(Long customerId) {
        return jpaRentalRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Rental> findActiveRentalsByCarId(Long carId) {
        return jpaRentalRepository.findActiveRentalsByCarId(
                carId, 
                Arrays.asList(RentalStatus.PENDING, RentalStatus.ACTIVE)
        );
    }

    @Override
    public void deleteById(Long id) {
        jpaRentalRepository.deleteById(id);
    }
}
