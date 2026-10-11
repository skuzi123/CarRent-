package com.carrent.boundedcontext.customer.application.mapper;

import com.carrent.boundedcontext.customer.application.dto.CustomerDTO;
import com.carrent.boundedcontext.customer.domain.model.aggregate.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getLicenseNumber(),
                customer.getLicenseExpiryDate(),
                customer.getStatus().name(),
                customer.getCreatedAt()
        );
    }
}
