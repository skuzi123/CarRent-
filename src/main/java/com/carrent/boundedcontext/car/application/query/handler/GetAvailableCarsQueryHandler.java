package com.carrent.boundedcontext.car.application.query.handler;

import com.carrent.boundedcontext.car.application.dto.CarDTO;
import com.carrent.boundedcontext.car.application.mapper.CarMapper;
import com.carrent.boundedcontext.car.application.query.GetAvailableCarsQuery;
import com.carrent.boundedcontext.car.domain.model.valueobject.CarStatus;
import com.carrent.boundedcontext.car.domain.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAvailableCarsQueryHandler {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public GetAvailableCarsQueryHandler(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    public List<CarDTO> handle(GetAvailableCarsQuery query) {
        return carRepository.findByStatus(CarStatus.AVAILABLE)
                .stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }
}
