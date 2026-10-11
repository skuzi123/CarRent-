package com.carrent.boundedcontext.customer.application.query.handler;

import com.carrent.boundedcontext.customer.application.dto.CustomerDTO;
import com.carrent.boundedcontext.customer.application.mapper.CustomerMapper;
import com.carrent.boundedcontext.customer.application.query.GetCustomerQuery;
import com.carrent.boundedcontext.customer.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerQueryHandler {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public GetCustomerQueryHandler(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDTO handle(GetCustomerQuery query) {
        return customerRepository.findById(query.getCustomerId())
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + query.getCustomerId()));
    }
}
