package com.carrent.boundedcontext.customer.infrastructure.repository;

import com.carrent.boundedcontext.customer.domain.model.aggregate.Customer;
import com.carrent.boundedcontext.customer.infrastructure.repository.persistence.CustomerJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerPersistenceMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Customer.restore(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getLicenseNumber(),
                entity.getLicenseExpiryDate(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public CustomerJpaEntity toEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        var entity = new CustomerJpaEntity();
        entity.setId(customer.getId());
        entity.setFirstName(customer.getFirstName());
        entity.setLastName(customer.getLastName());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        entity.setLicenseNumber(customer.getLicenseNumber());
        entity.setLicenseExpiryDate(customer.getLicenseExpiryDate());
        entity.setStatus(customer.getStatus());
        entity.setCreatedAt(customer.getCreatedAt());
        entity.setUpdatedAt(customer.getUpdatedAt());
        return entity;
    }
}
