package com.carrent.boundedcontext.customer.infrastructure.repository;

import com.carrent.boundedcontext.customer.infrastructure.repository.persistence.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerSpringDataRepository extends JpaRepository<CustomerJpaEntity, Long> {

    Optional<CustomerJpaEntity> findByEmail(String email);
}
