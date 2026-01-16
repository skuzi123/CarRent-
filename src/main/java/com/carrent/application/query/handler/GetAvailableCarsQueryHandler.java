package com.carrent.application.query.handler;

import com.carrent.application.dto.CarDTO;
import com.carrent.application.mapper.CarMapper;
import com.carrent.application.query.GetAvailableCarsQuery;
import com.carrent.domain.model.valueobject.CarStatus;
import com.carrent.domain.repository.CarRepository;
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
