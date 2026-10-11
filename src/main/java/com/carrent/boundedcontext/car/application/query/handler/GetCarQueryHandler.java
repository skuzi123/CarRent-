package com.carrent.boundedcontext.car.application.query.handler;

import com.carrent.boundedcontext.car.application.dto.CarDTO;
import com.carrent.boundedcontext.car.application.mapper.CarMapper;
import com.carrent.boundedcontext.car.application.query.GetCarQuery;
import com.carrent.boundedcontext.car.domain.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCarQueryHandler {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public GetCarQueryHandler(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public CarDTO handle(GetCarQuery query) {
        return carRepository.findById(query.getCarId())
                .map(carMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + query.getCarId()));
    }
}
