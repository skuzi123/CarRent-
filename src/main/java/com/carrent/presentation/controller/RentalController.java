package com.carrent.presentation.controller;

import com.carrent.application.command.CancelRentalCommand;
import com.carrent.application.command.RentCarCommand;
import com.carrent.application.command.ReturnCarCommand;
import com.carrent.application.command.handler.CancelRentalCommandHandler;
import com.carrent.application.command.handler.RentCarCommandHandler;
import com.carrent.application.command.handler.ReturnCarCommandHandler;
import com.carrent.application.dto.RentalDTO;
import com.carrent.application.query.GetCustomerRentalsQuery;
import com.carrent.application.query.GetRentalQuery;
import com.carrent.application.query.handler.GetCustomerRentalsQueryHandler;
import com.carrent.application.query.handler.GetRentalQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentCarCommandHandler rentCarCommandHandler;
    private final ReturnCarCommandHandler returnCarCommandHandler;
    private final CancelRentalCommandHandler cancelRentalCommandHandler;
    private final GetRentalQueryHandler getRentalQueryHandler;
    private final GetCustomerRentalsQueryHandler getCustomerRentalsQueryHandler;

    public RentalController(
            RentCarCommandHandler rentCarCommandHandler,
            ReturnCarCommandHandler returnCarCommandHandler,
            CancelRentalCommandHandler cancelRentalCommandHandler,
            GetRentalQueryHandler getRentalQueryHandler,
            GetCustomerRentalsQueryHandler getCustomerRentalsQueryHandler) {
        this.rentCarCommandHandler = rentCarCommandHandler;
        this.returnCarCommandHandler = returnCarCommandHandler;
        this.cancelRentalCommandHandler = cancelRentalCommandHandler;
        this.getRentalQueryHandler = getRentalQueryHandler;
        this.getCustomerRentalsQueryHandler = getCustomerRentalsQueryHandler;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> rentCar(@Valid @RequestBody RentCarCommand command) {
        Long rentalId = rentCarCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", rentalId));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnCar(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, Long> body) {
        Long newMileage = body != null ? body.get("newMileage") : null;
        returnCarCommandHandler.handle(new ReturnCarCommand(id, newMileage));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelRental(@PathVariable Long id) {
        cancelRentalCommandHandler.handle(new CancelRentalCommand(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable Long id) {
        RentalDTO rental = getRentalQueryHandler.handle(new GetRentalQuery(id));
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalDTO>> getCustomerRentals(@PathVariable Long customerId) {
        List<RentalDTO> rentals = getCustomerRentalsQueryHandler.handle(new GetCustomerRentalsQuery(customerId));
        return ResponseEntity.ok(rentals);
    }
}
