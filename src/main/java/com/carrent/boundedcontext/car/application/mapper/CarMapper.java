package com.carrent.boundedcontext.car.application.mapper;

import com.carrent.boundedcontext.car.application.dto.CarDTO;
import com.carrent.boundedcontext.car.domain.model.aggregate.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDTO toDTO(Car car) {
        if (car == null) {
            return null;
        }
        return new CarDTO(
                car.getId(),
                car.getSpecification().getMake(),
                car.getSpecification().getModel(),
                car.getSpecification().getYear(),
                car.getSpecification().getColor(),
                car.getLicensePlate().getNumber(),
                car.getLicensePlate().getCountry(),
                car.getMileage(),
                car.getStatus().name()
        );
    }
}
