package com.carrent.presentation.controller;

import com.carrent.application.command.CreateCustomerCommand;
import com.carrent.application.command.handler.CreateCustomerCommandHandler;
import com.carrent.application.dto.CustomerDTO;
import com.carrent.application.query.GetCustomerQuery;
import com.carrent.application.query.handler.GetCustomerQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerCommandHandler createCustomerCommandHandler;
    private final GetCustomerQueryHandler getCustomerQueryHandler;

    public CustomerController(
            CreateCustomerCommandHandler createCustomerCommandHandler,
            GetCustomerQueryHandler getCustomerQueryHandler) {
        this.createCustomerCommandHandler = createCustomerCommandHandler;
        this.getCustomerQueryHandler = getCustomerQueryHandler;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createCustomer(@Valid @RequestBody CreateCustomerCommand command) {
        Long customerId = createCustomerCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        CustomerDTO customer = getCustomerQueryHandler.handle(new GetCustomerQuery(id));
        return ResponseEntity.ok(customer);
    }
}
