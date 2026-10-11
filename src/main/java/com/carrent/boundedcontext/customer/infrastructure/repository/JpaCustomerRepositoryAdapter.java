package com.carrent.boundedcontext.customer.infrastructure.repository;

import com.carrent.boundedcontext.customer.domain.model.aggregate.Customer;
import com.carrent.boundedcontext.customer.domain.repository.CustomerRepository;
import com.carrent.boundedcontext.customer.infrastructure.repository.persistence.CustomerJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepository {

    private final CustomerSpringDataRepository springDataRepository;
    private final CustomerPersistenceMapper persistenceMapper;

    public JpaCustomerRepositoryAdapter(
            CustomerSpringDataRepository springDataRepository,
            CustomerPersistenceMapper persistenceMapper) {
        this.springDataRepository = springDataRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = persistenceMapper.toEntity(customer);
        CustomerJpaEntity saved = springDataRepository.save(entity);
        return persistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return springDataRepository.findById(id).map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return springDataRepository.findByEmail(email).map(persistenceMapper::toDomain);
    }

    @Override
    public List<Customer> findAll() {
        return springDataRepository.findAll().stream().map(persistenceMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
