package com.carrent.boundedcontext.customer.application.command.handler;

import com.carrent.boundedcontext.customer.application.command.CreateCustomerCommand;
import com.carrent.boundedcontext.customer.domain.model.aggregate.Customer;
import com.carrent.boundedcontext.customer.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCustomerCommandHandler {

    private final CustomerRepository customerRepository;

    public CreateCustomerCommandHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Long handle(CreateCustomerCommand command) {
        var customer = new Customer(
                command.getFirstName(),
                command.getLastName(),
                command.getEmail(),
                command.getPhone(),
                command.getLicenseNumber(),
                command.getLicenseExpiryDate()
        );
        
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }
}
