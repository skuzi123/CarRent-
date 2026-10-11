package com.carrent.boundedcontext.customer.domain.repository;

import com.carrent.boundedcontext.customer.domain.model.aggregate.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAll();
    void deleteById(Long id);
}
