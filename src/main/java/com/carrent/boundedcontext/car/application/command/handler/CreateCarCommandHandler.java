package com.carrent.boundedcontext.car.application.command.handler;

import com.carrent.boundedcontext.car.application.command.CreateCarCommand;
import com.carrent.boundedcontext.car.domain.model.aggregate.Car;
import com.carrent.boundedcontext.car.domain.model.valueobject.CarSpecification;
import com.carrent.boundedcontext.car.domain.model.valueobject.LicensePlate;
import com.carrent.boundedcontext.car.domain.repository.CarRepository;
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
        var specification = new CarSpecification(
                command.getMake(),
                command.getModel(),
                command.getYear(),
                command.getColor()
        );
        
        var licensePlate = new LicensePlate(
                command.getLicensePlateNumber(),
                command.getLicensePlateCountry()
        );
        
        var car = new Car(specification, licensePlate, command.getMileage());
        Car savedCar = carRepository.save(car);
        
        return savedCar.getId();
    }
}
