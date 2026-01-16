package com.carrent.application.command.handler;

import com.carrent.application.command.CreateCarCommand;
import com.carrent.domain.model.aggregate.Car;
import com.carrent.domain.model.valueobject.CarSpecification;
import com.carrent.domain.model.valueobject.LicensePlate;
import com.carrent.domain.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCarCommandHandler {

    private final CarRepository carRepository;

    public CreateCarCommandHandler(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public Long handle(CreateCarCommand command) {
        CarSpecification specification = new CarSpecification(
                command.getMake(),
                command.getModel(),
                command.getYear(),
                command.getColor()
        );
        
        LicensePlate licensePlate = new LicensePlate(
                command.getLicensePlateNumber(),
                command.getLicensePlateCountry()
        );
        
        Car car = new Car(specification, licensePlate, command.getMileage());
        Car savedCar = carRepository.save(car);
        
        return savedCar.getId();
    }
}
