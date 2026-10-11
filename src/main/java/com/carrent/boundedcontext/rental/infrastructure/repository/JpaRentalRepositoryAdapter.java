package com.carrent.boundedcontext.rental.infrastructure.repository;

import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalStatus;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import com.carrent.boundedcontext.rental.infrastructure.repository.persistence.RentalJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JpaRentalRepositoryAdapter implements RentalRepository {

    private final RentalSpringDataRepository springDataRepository;
    private final RentalPersistenceMapper persistenceMapper;

    public JpaRentalRepositoryAdapter(
            RentalSpringDataRepository springDataRepository,
            RentalPersistenceMapper persistenceMapper) {
        this.springDataRepository = springDataRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Rental save(Rental rental) {
        RentalJpaEntity entity = persistenceMapper.toEntity(rental);
        RentalJpaEntity saved = springDataRepository.save(entity);
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Rental> findById(Long id) {
        return springDataRepository.findById(id).map(persistenceMapper::toDomain);
    }

    @Override
    public List<Rental> findAll() {
        return springDataRepository.findAll().stream().map(persistenceMapper::toDomain).toList();
    }

    @Override
    public List<Rental> findByCustomerId(Long customerId) {
        return springDataRepository.findByCustomerId(customerId).stream()
                .map(persistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Rental> findActiveRentalsByCarId(Long carId) {
        return springDataRepository.findActiveRentalsByCarId(
                carId,
                Arrays.asList(RentalStatus.PENDING, RentalStatus.ACTIVE)
        ).stream().map(persistenceMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
