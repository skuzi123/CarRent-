package com.carrent.boundedcontext.car.application.event.handler;

import com.carrent.boundedcontext.car.domain.model.aggregate.Car;
import com.carrent.boundedcontext.car.domain.repository.CarRepository;
import com.carrent.integration.event.RentalCancelledIntegrationEvent;
import com.carrent.integration.event.RentalCompletedIntegrationEvent;
import com.carrent.integration.event.RentalCreatedIntegrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentalIntegrationEventHandler {

    private final CarRepository carRepository;

    public RentalIntegrationEventHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @EventListener
    @Transactional
    public void onRentalCreated(RentalCreatedIntegrationEvent event) {
        Car car = findCar(event.getCarId());
        car.rent();
        carRepository.save(car);
    }

    @EventListener
    @Transactional
    public void onRentalCompleted(RentalCompletedIntegrationEvent event) {
        Car car = findCar(event.getCarId());
        car.returnCar(null);
        carRepository.save(car);
    }

    @EventListener
    @Transactional
    public void onRentalCancelled(RentalCancelledIntegrationEvent event) {
        Car car = findCar(event.getCarId());
        car.returnCar(null);
        carRepository.save(car);
    }

    private Car findCar(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + carId));
    }
}
