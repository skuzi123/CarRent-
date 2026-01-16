package com.carrent.infrastructure.repository;

import com.carrent.domain.model.aggregate.Customer;
import com.carrent.domain.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
