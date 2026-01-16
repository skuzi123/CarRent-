package com.carrent.presentation.controller;

import com.carrent.application.command.CreateCarCommand;
import com.carrent.application.command.handler.CreateCarCommandHandler;
import com.carrent.application.dto.CarDTO;
import com.carrent.application.query.GetAvailableCarsQuery;
import com.carrent.application.query.GetCarQuery;
import com.carrent.application.query.handler.GetAvailableCarsQueryHandler;
import com.carrent.application.query.handler.GetCarQueryHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CreateCarCommandHandler createCarCommandHandler;
    private final GetCarQueryHandler getCarQueryHandler;
    private final GetAvailableCarsQueryHandler getAvailableCarsQueryHandler;

    public CarController(
            CreateCarCommandHandler createCarCommandHandler,
            GetCarQueryHandler getCarQueryHandler,
            GetAvailableCarsQueryHandler getAvailableCarsQueryHandler) {
        this.createCarCommandHandler = createCarCommandHandler;
        this.getCarQueryHandler = getCarQueryHandler;
        this.getAvailableCarsQueryHandler = getAvailableCarsQueryHandler;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createCar(@Valid @RequestBody CreateCarCommand command) {
        Long carId = createCarCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", carId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCar(@PathVariable Long id) {
        CarDTO car = getCarQueryHandler.handle(new GetCarQuery(id));
        return ResponseEntity.ok(car);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getAvailableCars() {
        List<CarDTO> cars = getAvailableCarsQueryHandler.handle(new GetAvailableCarsQuery());
        return ResponseEntity.ok(cars);
    }
}
