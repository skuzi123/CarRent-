package com.carrent.boundedcontext.car.domain.model.valueobject;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CarSpecification {
    String make;
    String model;
    Integer year;
    String color;

    public CarSpecification(String make, String model, Integer year, String color) {
        if (make == null || make.isBlank()) {
            throw new IllegalArgumentException("Make cannot be null or empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (year == null || year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
    }
}
