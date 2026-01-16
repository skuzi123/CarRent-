package com.carrent.infrastructure.repository;

import com.carrent.domain.model.aggregate.Customer;
import com.carrent.domain.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    public JpaCustomerRepositoryAdapter(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return jpaCustomerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaCustomerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return jpaCustomerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaCustomerRepository.deleteById(id);
    }
}
