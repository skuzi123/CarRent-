package com.carrent.application.query.handler;

import com.carrent.application.dto.CarDTO;
import com.carrent.application.mapper.CarMapper;
import com.carrent.application.query.GetCarQuery;
import com.carrent.domain.repository.CarRepository;
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
